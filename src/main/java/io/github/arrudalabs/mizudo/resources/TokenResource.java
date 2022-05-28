package io.github.arrudalabs.mizudo.resources;

import io.github.arrudalabs.mizudo.services.Credentials;
import io.github.arrudalabs.mizudo.services.SecurityService;
import io.github.arrudalabs.mizudo.services.Token;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@ApplicationScoped
@Path("/")
public class TokenResource {

    @Inject
    SecurityService securityService;

    @POST
    @Path("token")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Produces()
    public Token generateToken(@FormParam("username")
                               String username,
                               @FormParam("password")
                               String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        return securityService
                .auth(new Credentials(username, password))
                .orElseThrow(() -> new WebApplicationException("unauthorized_client", Response.Status.UNAUTHORIZED.getStatusCode()));
    }

}
