package io.github.arrudalabs.mizudo.resources;

import io.quarkus.vertx.web.RouteFilter;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import org.jboss.resteasy.reactive.RestResponse;

import javax.enterprise.context.ApplicationScoped;
import java.util.Set;

@ApplicationScoped
public class AngularRouteSupport {

    private final Vertx vertx;
    private final Set<String> apiPaths = Set.of(MizudoResources.APPLICATION_PATH, "/q/");

    public AngularRouteSupport(Vertx vertx) {
        this.vertx = vertx;
    }

    @RouteFilter
    void route(RoutingContext rc) {
        if (isNotAPIRequest(rc)) {
            rc.response()
                    .setStatusCode(RestResponse.StatusCode.OK)
                    .send(vertx.fileSystem().readFileBlocking("META-INF/resources/index.html"));
            return;
        }
        rc.next();
    }

    private boolean isNotAPIRequest(RoutingContext rc) {
        return isGET(rc.request().method())
                && !assetExists(rc.normalizedPath())
                && !isOnApiPaths(rc.normalizedPath());
    }

    private boolean isOnApiPaths(String path) {
        return this.apiPaths
                .stream().filter(apiPath -> path.startsWith(apiPath))
                .findFirst().isPresent();
    }

    private boolean assetExists(String path) {
        return vertx.fileSystem().existsBlocking("META-INF/resources%s".formatted(path));
    }

    private boolean isGET(HttpMethod method) {
        return HttpMethod.GET.equals(method);
    }

}
