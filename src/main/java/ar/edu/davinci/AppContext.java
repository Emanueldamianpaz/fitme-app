package ar.edu.davinci;

import ar.edu.davinci.domain.dto.ResponseError;
import ar.edu.davinci.infraestructure.Router;
import ar.edu.davinci.infraestructure.exception.FitmeException;
import ar.edu.davinci.infraestructure.exception.runtime.*;
import ar.edu.davinci.infraestructure.security.SecurityHandler;
import ar.edu.davinci.infraestructure.security.filters.ClientFilter;
import ar.edu.davinci.infraestructure.security.filters.CoachFilter;
import com.github.racc.tscg.TypesafeConfig;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.http.HttpStatus;
import spark.Filter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;

import static spark.Spark.*;

@Slf4j
@Singleton
public class AppContext {

    private final Integer port;
    private final String basePath;
    private final String apiPath;
    private String url;
    private SecurityHandler securityHandler;
    private CoachFilter coachFilter;
    private ClientFilter clientFilter;

    private Gson jsonTransformer;

    private final Set<Router> routers;

    @Inject
    public AppContext(
            @TypesafeConfig("server.port") Integer port,
            @TypesafeConfig("app.api") String apiPath,
            @TypesafeConfig("app.context") String basePath,
            @TypesafeConfig("app.url") String url,
            CoachFilter coachFilter,
            ClientFilter clientFilter,
            Gson jsonTransformer,
            Set<Router> routers,
            SecurityHandler securityHandler
    ) {

        this.port = port;
        this.basePath = basePath;
        this.apiPath = apiPath;
        this.url = url;
        this.jsonTransformer = jsonTransformer;
        this.routers = routers;
        this.url = url;
        this.securityHandler = securityHandler;
        this.clientFilter = clientFilter;
        this.coachFilter = coachFilter;
    }

    void init() {
        log.info("API endpoint is {}", url + "/");

        port(port);

        this.configureCors();

        this.configureAuth();

        this.configureExceptions();

        this.configureRoutes();

        this.configureContentTypes();

    }

    private void configureAuth() {
        //  before(basePath + "/*", securityFilter, roleAssignedFilter, readOnlyFilter);
        before(basePath + "/*", securityHandler);
    }

    private void configureCors() {

        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Request-Method", "GET,PUT,POST,DELETE,OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
            response.header("Access-Control-Allow-Credentials", "true");
        });
    }

    private void configureContentTypes() {
        Filter contentTypeFilter = (req, resp) -> resp.type("application/json");

        afterAfter(basePath + apiPath + "/*", contentTypeFilter);
        afterAfter(basePath + "/user*", contentTypeFilter);

    }

    private void configureExceptions() {

        exception(InternalServerErrorException.class, (e, request, response) -> {
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            response.body(jsonTransformer.toJson(
                    new ResponseError(
                            e.getCause().getClass().getName(),
                            e.getCause().getMessage()
                    ))
            );
        });

        exception(FitmeException.class, (e, request, response) -> {
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            response.body(jsonTransformer.toJson(
                    new ResponseError(
                            FitmeException.class.getSimpleName(),
                            e.getMessage()
                    ))
            );
        });

        exception(BusinessConstrainException.class, (e, request, response) -> {
            response.status(HttpStatus.BAD_REQUEST_400);
            response.body(jsonTransformer.toJson(
                    new ResponseError(
                            BusinessConstrainException.class.getSimpleName(),
                            e.getMessage()
                    ))
            );
        });

        exception(MissingParameterException.class, (e, request, response) -> {
            response.status(HttpStatus.BAD_REQUEST_400);
            response.body(jsonTransformer.toJson(
                    new ResponseError(
                            MissingParameterException.class.getSimpleName(),
                            e.getMessage()
                    ))
            );
        });

        exception(ResourceNotFoundException.class, (e, request, response) -> {
            response.status(HttpStatus.NOT_FOUND_404);
            response.body(jsonTransformer.toJson(
                    new ResponseError(
                            ResourceNotFoundException.class.getSimpleName(),
                            e.getMessage()
                    ))
            );
        });

        exception(InvalidParameterException.class, (e, request, response) -> {
            response.status(HttpStatus.BAD_REQUEST_400);
            response.body(jsonTransformer.toJson(
                    new ResponseError(
                            InvalidParameterException.class.getSimpleName(),
                            e.getMessage()
                    ))
            );
        });

        exception(UnauthorizedRequestException.class, (e, request, response) -> {
            response.status(HttpStatus.UNAUTHORIZED_401);
            response.body(jsonTransformer.toJson(
                    new ResponseError(
                            UnauthorizedRequestException.class.getSimpleName(),
                            e.getMessage()
                    ))
            );
        });
    }

    private void configureRoutes() {
        routers.forEach(this::configureRoute);
    }

    private void configureRoute(Router router) {

        String fullPath = basePath + router.path();

        log.info("Define route {}", fullPath);

        path(fullPath, router.routes());
    }
}
