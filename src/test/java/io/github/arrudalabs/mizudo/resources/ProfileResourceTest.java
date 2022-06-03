package io.github.arrudalabs.mizudo.resources;

import io.github.arrudalabs.mizudo.BaseTest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.authentication.OAuthSignature;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class ProfileResourceTest extends BaseTest{

    @Test
    @DisplayName("when the admin user request GET /resources/profiles/me then a valid profile object")
    public void testGet(){
            given()
                    .auth().oauth2(getAdminToken(), OAuthSignature.HEADER)
                    .when()
                    .get("/resources/profiles/me")
                    .then()
                    .statusCode(RestResponse.StatusCode.OK)
                    .body("username",is(getAdminUsername()));
    }



}
