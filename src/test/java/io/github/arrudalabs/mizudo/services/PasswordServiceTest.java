package io.github.arrudalabs.mizudo.services;

import io.github.arrudalabs.mizudo.BaseTest;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
class PasswordServiceTest extends BaseTest {

    @Inject
    PasswordService passwordService;

    @Test
    void testHappyFlow() {
        // salt
        String rawPassword = faker.internet().password();

        GeneratedPassword generatedPassword = passwordService.newPassword(rawPassword);
        assertGeneratedPasswordFromNewPasswordMethod(generatedPassword);

        GeneratedPassword recoveredPassword = passwordService.passwordFrom(generatedPassword.salt(), rawPassword);
        assertGeneratedPasswordFromPasswordFromMethod(recoveredPassword);

        assertEquals(generatedPassword.salt(), recoveredPassword.salt(), "both methods,passwordService.newPassword() and passwordService.passwordFrom(), aren't generating the expected password's salt");
        assertEquals(generatedPassword.hash(), recoveredPassword.hash(), "both methods,passwordService.newPassword() and passwordService.passwordFrom(), aren't generating the expected password's hash");
    }

    private void assertGeneratedPasswordFromNewPasswordMethod(GeneratedPassword generatedPassword) {
        assertNotNull(generatedPassword, "passwordService.newPassword() cannot return a nullable reference");
        assertNotNull(generatedPassword.salt(), "generated password's salt from passwordService.newPassword() cannot be a nullable reference");
        assertNotNull(generatedPassword.hash(), "generated password's hash from passwordService.newPassword() cannot be a nullable reference");
    }

    private void assertGeneratedPasswordFromPasswordFromMethod(GeneratedPassword generatedPassword) {
        assertNotNull(generatedPassword, "passwordService.passwordFrom() cannot return a nullable reference");
        assertNotNull(generatedPassword.salt(), "generated password's salt from passwordService.passwordFrom() cannot be a nullable reference");
        assertNotNull(generatedPassword.hash(), "generated password's hash from passwordService.passwordFrom() cannot be a nullable reference");
    }

    @Test
    void testBadFlow() {
        // salt
        String rawPassword = faker.internet().password();
        GeneratedPassword generatedPassword = passwordService.newPassword(rawPassword);
        assertGeneratedPasswordFromNewPasswordMethod(generatedPassword);

        String anotherRawPassword = faker.internet().password();
        GeneratedPassword recoveredPassword = passwordService.passwordFrom(generatedPassword.salt(), anotherRawPassword);
        assertGeneratedPasswordFromPasswordFromMethod(recoveredPassword);

        assertEquals(generatedPassword.salt(), recoveredPassword.salt(), "both methods,passwordService.newPassword() and passwordService.passwordFrom(), aren't generating the expected password's salt");
        assertThat("password's hashes from different rawPasswords must generate different hashes",
                generatedPassword.hash(), not(equalTo(recoveredPassword.hash())));
    }
}