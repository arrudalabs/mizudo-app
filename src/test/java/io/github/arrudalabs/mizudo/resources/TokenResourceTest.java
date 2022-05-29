package io.github.arrudalabs.mizudo.resources;


import io.github.arrudalabs.mizudo.BaseTest;
import io.github.arrudalabs.mizudo.entities.User;
import io.github.arrudalabs.mizudo.services.PasswordService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class TokenResourceTest extends BaseTest {

    @Inject
    PasswordService passwordService;

    @BeforeEach
    void createAdminUser() {
        this.execute(() -> {
            User.deleteAllUsers();
            User.createUser("admin", passwordService.newPassword("admin")).persist();
        });
    }

    @AfterEach
    void deleteAdminUser() {
        this.execute(User::deleteAllUsers);
    }

    @Test
    @DisplayName("given a valid user/password when request a new JWT token then should returns a valid JWT token")
    void testTokenEndpoint() {
        given().
                when()
                .log().ifValidationFails()
                .contentType(ContentType.URLENC)
                .accept(ContentType.JSON)
                .params(Map.of(
                        "username", "admin",
                        "password", "admin"))
                .post("/resources/token")
                .then()
                .log().ifValidationFails()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("access_token", not(emptyString()))
                .body("expires_in", any(Integer.class));

    }

}
