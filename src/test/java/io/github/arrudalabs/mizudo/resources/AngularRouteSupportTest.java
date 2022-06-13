package io.github.arrudalabs.mizudo.resources;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.restassured.RestAssured.*;

import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class AngularRouteSupportTest {

    @ParameterizedTest()
    @CsvSource(
            textBlock = """
                    /
                    /any
                    """
    )
    void shouldReturnIndex(String uri) throws IOException {
        given()
                .when()
                .get(uri)
                .prettyPeek()
                .then()
                .statusCode(200)
                .body(is(Files.readString(Path.of("src/test/resources/META-INF/resources/index.html"))));;
    }

    @Test()
    void shouldReturnAnExistentAsset() throws IOException {
        given()
                .when()
                .get("/dummy.txt")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body(is(Files.readString(Path.of("src/test/resources/META-INF/resources/dummy.txt"))));
    }

    @Test()
    void shouldReturnAnExistentAssetWhenRequestWithParams() throws IOException {
        given()
                .when()
                .get("/dummy.txt?q1=A&q1=B")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body(is(Files.readString(Path.of("src/test/resources/META-INF/resources/dummy.txt"))));
    }


    @Test()
    void requestToSwaggerUIShouldWorks() throws IOException {
        given()
                .when()
                .get("/q/swagger-ui/index.html")
                .prettyPeek()
                .then()
                .statusCode(200);
    }

}
