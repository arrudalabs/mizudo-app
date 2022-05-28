package org.acme;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class JwtTokenBuilder {

    private final String privateKeyId = UUID.randomUUID().toString();

    @ConfigProperty(name = "jwt.privateKey")
    private String privateKey;

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    private String issuer;

    public String generateToken(String username,
                                Set<String> roles,
                                int durationInSeconds) throws NoSuchAlgorithmException, InvalidKeySpecException {

        JwtClaimsBuilder claims = Jwt.claims();

        var currentSecond = System.currentTimeMillis() / 1000;

        claims.subject(username);
        claims.issuer(this.issuer);
        claims.issuedAt(currentSecond);
        claims.expiresAt(currentSecond + durationInSeconds);
        claims.groups(roles);

        PrivateKey privateKey = getPrivateKey();

        String token = claims.jws().keyId(this.privateKeyId).sign(privateKey);

        return token;
    }

    private PrivateKey getPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] encodedBytes = Base64.getDecoder().decode(this.privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePrivate(keySpec);
    }

    public static void main(String[] args) throws IOException {

        System.out.println("starting...");
        Files.writeString(Path.of("publickey-string.txt"),
                Files.readAllLines(Path.of("publicKey.pem"))
                        .stream()
                        .filter(line -> !line.contains("-----BEGIN"))
                        .filter(line -> !line.contains("-----END"))
                        .collect(Collectors.joining())
                ,StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);

        System.out.println("done!");

    }
}
