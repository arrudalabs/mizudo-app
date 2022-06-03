package io.github.arrudalabs.mizudo;


import com.github.javafaker.Faker;
import io.github.arrudalabs.mizudo.services.JwtTokenService;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Set;
import java.util.function.Supplier;

public class BaseTest {

    public final Faker faker = new Faker();

    @Transactional
    public void execute(Runnable runnable) {
        runnable.run();
    }

    @Transactional
    public <T> T executeAndReturn(Supplier<T> supplier) {
        return supplier.get();
    }

    @Inject
    @ConfigProperty(name = "admin.password",defaultValue = "shoto")
    private String adminPassword;

    @Inject
    @ConfigProperty(name = "jwt.accessToken.durationInSeconds", defaultValue = "3600")
    private Integer expiresIn;

    @Inject
    private JwtTokenService jwtTokenService;

    public String getAdminToken(){
        try {
            return jwtTokenService.generateToken(getAdminUsername(), Set.of(),expiresIn);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public String getAdminUsername() {
        return "admin";
    }
}
