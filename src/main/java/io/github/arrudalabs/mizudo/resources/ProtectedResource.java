package org.acme;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.Set;

@Path("others")
@RequestScoped
public class ProtectedResource {

    @Inject
    JwtTokenBuilder jwtTokenBuilder;

    @POST
    @Path("auth")
    public Response auth(Credentials credentials) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String jwt = jwtTokenBuilder.generateToken(
                credentials.username,
                Set.of("admin"),
                60);
        return Response.ok(new Token(jwt)).build();
    }
    record Token(String access_token){}
    record Credentials(String username, String password){}


    @GET
    @Path("hello")
    @PermitAll
    public Response hello(@QueryParam("name") @DefaultValue("anonymous") String name){
        return Response.ok(Map.of("message","Hello %s".formatted(name))).build();
    }

    @GET
    @Path("secure-hello")
    @RolesAllowed({"admin"})
    public Response secureHello(@QueryParam("name") @DefaultValue("anonymous") String name){
        return Response.ok(Map.of("message","Hello %s".formatted(name))).build();
    }
}
