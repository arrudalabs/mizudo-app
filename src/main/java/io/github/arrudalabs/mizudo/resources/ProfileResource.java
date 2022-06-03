package io.github.arrudalabs.mizudo.resources;

import io.quarkus.security.Authenticated;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Map;

@Path("profiles")
public class ProfileResource {

    @Inject
    JsonWebToken token;

    @GET
    @Path("me")
    @Authenticated
    public Response get(){
        return Response.ok(
                Map.of("username",
                        token.getName())
        ).build();
    }
}
