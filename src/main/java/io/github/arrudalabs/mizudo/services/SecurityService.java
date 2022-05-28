package io.github.arrudalabs.mizudo.services;

import io.github.arrudalabs.mizudo.entities.User;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Optional;

@ApplicationScoped
public class SecurityService {

    @Inject
    private JwtTokenService jwtTokenService;

    @Inject
    @ConfigProperty(name = "jwt.accessToken.durationInSeconds", defaultValue = "3600")
    private Integer expiresIn;

    @Inject
    private PasswordService passwordService;


    public Optional<Token> auth(Credentials credentials) throws NoSuchAlgorithmException, InvalidKeySpecException {
        User foundUser = User.findByUsername(credentials.username());
        if (foundUser == null)
            return Optional.empty();
        GeneratedPassword generatedPassword = passwordService.passwordFrom(foundUser.salt, credentials.password());
        if (Arrays.equals(generatedPassword.hash(), foundUser.hash)) {
            return Optional.of(new Token(generateAccessToken(foundUser, this.expiresIn), this.expiresIn));
        }
        return Optional.empty();
    }

    private String generateAccessToken(User foundUser, Integer expiresIn) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return this.jwtTokenService
                .generateToken(foundUser.username, foundUser.roles, expiresIn);
    }
}
