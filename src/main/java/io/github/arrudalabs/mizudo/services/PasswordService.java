package io.github.arrudalabs.mizudo.services;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@ApplicationScoped
public class PasswordService {

    // Password-Based Encryption (PBE)
    // Key Derivation Function (KDFs)
    // PBKDF2 -> HMAC
    // SALT

    @Inject
    @ConfigProperty(name = "password.algorithm", defaultValue = "PBKDF2WithHmacSHA512")
    private String algorithm;
    @Inject
    @ConfigProperty(name = "password.salt.length", defaultValue = "16")
    private int saltLength;
    @Inject
    @ConfigProperty(name = "password.iteration.count", defaultValue = "50000")
    private int iterationCount;
    @Inject
    @ConfigProperty(name = "password.size", defaultValue = "32")
    private int passwordSize;

    public GeneratedPassword newPassword(String rawPassword) {
        return passwordFrom(createSalt(), rawPassword);
    }

    private String createSalt() {
        SecureRandom secRan = new SecureRandom() ; // In Unix like systems, default constructor uses NativePRNG, seeded by securerandom.source property
        byte[] salt = new byte[this.saltLength] ;
        secRan.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public GeneratedPassword passwordFrom(String salt, String rawPassword) {
        SecretKeyFactory keyFactory = null;
        try {
            keyFactory = SecretKeyFactory.getInstance(this.algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        KeySpec keySpec = new PBEKeySpec(
                rawPassword.toCharArray(),
                Base64.getDecoder().decode(salt),
                this.iterationCount,
                this.passwordSize);
        byte[] hash = new byte[0];
        try {
            hash = keyFactory.generateSecret(keySpec).getEncoded();
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return GeneratedPassword.fromSaltAndHash(salt,
                Base64.getEncoder().encodeToString(hash));
    }
}
