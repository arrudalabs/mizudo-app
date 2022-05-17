package org.acme;

import io.quarkus.hibernate.orm.panache.runtime.JpaOperations;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Path("/developers")
@Singleton
public class DeveloperResource {

    @GET
    public List<Developer> listAll() {
        return Developer.listAll();
    }

    @Path("{id}")
    @GET
    public Developer get(@PathParam("id") Long id) {
        return (Developer) Developer
                .findByIdOptional(id)
                .orElseThrow(() ->
                        new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @POST
    @Transactional
    public Developer add(DeveloperName developerName) {
        Developer developer = Developer.of(developerName.name());
        developer.persist();
        return developer;
    }

    @Path("{id}")
    @PUT
    @Transactional
    public Developer update(@PathParam("id") Long id, DeveloperName developerName) {
        Developer developer = (Developer) Developer
                .findByIdOptional(id)
                .orElseThrow(() ->
                        new WebApplicationException(Response.Status.NOT_FOUND));
        developer.setName(developerName.name());
        return developer;
    }

    record DeveloperName(String name) {
    }

    @Path("{id}")
    @DELETE
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        return Developer
                .findByIdOptional(id)
                .stream()
                .peek(dev -> dev.delete())
                .map(d -> Response.noContent().build())
                .findFirst()
                .orElseThrow(() ->
                        new WebApplicationException(Response.Status.NOT_FOUND));
    }


    @Transactional
    public void generateData(
            @Observes
            StartupEvent event
    ) {
        LongStream.rangeClosed(1l, 5l)
                .boxed()
                .map(idx -> Developer.of("Developer %s".formatted(idx)))
                .filter(dev -> Developer.count("name", dev.getName()) == 0)
                .forEach(dev -> dev.persist());
    }

}
