package io.github.arrudalabs.mizudo.resources;

import org.eclipse.microprofile.openapi.annotations.Components;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.security.*;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/resources")
@OpenAPIDefinition(
        info = @Info(
                title = "Mizu-do API",
                version = "1.0"
        ),
        security = {
                @SecurityRequirement(
                        name = "oauth2"
                )
        },
        components = @Components(
                securitySchemes = {
                        @SecurityScheme(
                                securitySchemeName = "oauth2",
                                type = SecuritySchemeType.OAUTH2,
                                flows = @OAuthFlows(
                                        password = @OAuthFlow(
                                                tokenUrl = "/resources/token",
                                                scopes = {}
                                        )
                                )

                        )
                }
        )
)
public class MizudoResources extends Application {
}
