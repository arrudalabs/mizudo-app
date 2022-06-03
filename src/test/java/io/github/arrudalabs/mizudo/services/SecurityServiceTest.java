package io.github.arrudalabs.mizudo.services;

import io.github.arrudalabs.mizudo.BaseTest;
import io.github.arrudalabs.mizudo.entities.User;
import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class SecurityServiceTest extends BaseTest {

    @Inject
    SecurityService securityService;

    @Inject
    PasswordService passwordService;

    @Test
    @DisplayName("given a valid username/password then should return a token")
    void testAuthWithValidCredentials() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Credentials credentials = createValidUsernameAndPassword();
        Optional<Token> token = securityService.auth(credentials);
        assertThat(token, notNullValue());
        assertThat(token.isPresent(),is(true));
        token.ifPresent(this::validateToken);

    }

    @Test
    @DisplayName("given a invalid username/password then cannot return a token instance")
    void testAuthWithInvalidCredentials() throws NoSuchAlgorithmException, InvalidKeySpecException {
        createValidUsernameAndPassword();
        Credentials invalidCredentials = new Credentials(faker.internet().emailAddress(),faker.internet().password());
        Optional<Token> token = securityService.auth(invalidCredentials);
        assertThat(token, notNullValue());
        assertThat(token.isEmpty(),is(true));
    }


    @Test
    @DisplayName("given a valid admin credentials then should return a token")
    void testAuthWithValidAdminCredentials() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Credentials credentials = createValidAdminCredentials();
        Optional<Token> token = securityService.auth(credentials);
        assertThat("must return a non nullable Optional<Token> reference",token, notNullValue());
        assertThat("provided Optional<Token> must have a valid reference",token.isPresent(),is(true));
        token.ifPresent(this::validateToken);

    }

    @Inject
    @ConfigProperty(name = "admin.password", defaultValue = "shoto")
    String adminPassword;

    private Credentials createValidAdminCredentials() {
        return new Credentials("admin", this.adminPassword);
    }


    private void validateToken(Token token) {
        assertThat(token.access_token(), not(emptyOrNullString()));
        assertThat(token.expires_in(), notNullValue());
    }

    @BeforeEach
    public void setup() {
        this.execute(User::deleteAllUsers);
    }

    @AfterEach
    public void clearDatabase(){
        this.execute(User::deleteAllUsers);
    }


    private Credentials createValidUsernameAndPassword() {
        return this.executeAndReturn(() -> {
            var username = faker.internet().emailAddress();
            var password = faker.internet().password();
            User user = User.createUser(username, passwordService.newPassword(password));
            user.persist();
            return new Credentials(username, password);
        });
    }

}
