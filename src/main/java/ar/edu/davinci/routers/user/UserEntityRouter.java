package ar.edu.davinci.routers.user;

import ar.edu.davinci.domain.model.User;
import ar.edu.davinci.infraestructure.security.util.FitmeUser;
import ar.edu.davinci.routers.FitmeRouter;
import ar.edu.davinci.service.user.UserEntityService;
import ar.edu.davinci.utils.JsonTransformer;
import com.github.racc.tscg.TypesafeConfig;
import com.google.gson.Gson;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.RouteGroup;

import javax.inject.Inject;

import static spark.Spark.*;

public class UserEntityRouter extends FitmeRouter {

    private String apiPath;
    private JsonTransformer jsonTransformer;
    private UserEntityService userEntityService;

    @Inject
    public UserEntityRouter(Gson objectMapper,
                            UserEntityService userEntityService,
                            SessionFactory sessionFactory,
                            JsonTransformer jsonTransformer,
                            @TypesafeConfig("app.api") String apiPath) {
        super(objectMapper, sessionFactory);
        this.apiPath = apiPath;
        this.userEntityService = userEntityService;
        this.jsonTransformer = jsonTransformer;
    }

    @Override
    public String path() {
        return apiPath + "/user";
    }

    @Override
    public RouteGroup routes() {
        return () -> {
            get("", getUsers, jsonTransformer);
            post("", createUser, jsonTransformer);
            patch("/:id", updateUserSession, jsonTransformer);
        };
    }


    private final Route getUsers = doInTransaction(false, (Request request, Response response) ->
            userEntityService.findAll()
    );

    private final Route createUser = doInTransaction(false, (Request request, Response response) ->
            {
                FitmeUser user = (FitmeUser) jsonTransformer.asJson(request.body(), FitmeUser.class);
                return userEntityService.create(new User(user));
            }
    );

    private final Route updateUserSession = doInTransaction(true, (Request request, Response response) ->
            {
                FitmeUser user = (FitmeUser) jsonTransformer.asJson(request.body(), FitmeUser.class);
                return userEntityService.update(new User(user));
            }
    );

}