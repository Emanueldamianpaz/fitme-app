package ar.edu.davinci.routers.user;

import ar.edu.davinci.domain.model.User;
import ar.edu.davinci.infraestructure.security.UserSessionFactory;
import ar.edu.davinci.infraestructure.security.util.FitmeUser;
import ar.edu.davinci.routers.FitmeRouter;
import ar.edu.davinci.service.user.UserEntityService;
import ar.edu.davinci.utils.JsonTransformer;
import com.auth0.SessionUtils;
import com.auth0.Tokens;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.RouteGroup;

import javax.inject.Inject;

import static ar.edu.davinci.infraestructure.security.SecurityFilter.authClient;
import static spark.Spark.*;

@Slf4j
public class UserEntityRouter extends FitmeRouter {

    private JsonTransformer jsonTransformer;
    private UserEntityService userEntityService;
    private UserSessionFactory userSessionFactory;

    @Inject
    public UserEntityRouter(Gson objectMapper,
                            UserEntityService userEntityService,
                            SessionFactory sessionFactory,
                            JsonTransformer jsonTransformer,
                            UserSessionFactory userSessionFactory) {
        super(objectMapper, sessionFactory);
        this.userEntityService = userEntityService;
        this.jsonTransformer = jsonTransformer;
        this.userSessionFactory = userSessionFactory;
    }

    @Override
    public String path() {
        return "/user";
    }

    @Override
    public RouteGroup routes() {
        return () -> {
            get("", getUsers, jsonTransformer);
            post("", createUser, jsonTransformer);
            patch("/:id", updateUserSession, jsonTransformer);
            get("/callback", createSession, jsonTransformer);

        };
    }


    private final Route getUsers = doInTransaction(false, (Request request, Response response) ->
            userEntityService.findAll()
    );


    private final Route createSession = (Request request, Response response) ->
    {
        log.info("Initializing Modules...");

        Tokens tokens = authClient.handle(request.raw());
        SessionUtils.set(request.raw(), "accessToken", tokens.getAccessToken());
        SessionUtils.set(request.raw(), "idToken", tokens.getIdToken());

        DecodedJWT jwt = JWT.decode(tokens.getIdToken());
//        DecodedJWT jwtVerified = verifier.verify(tokens.getIdToken());


        request.attribute("current-session", userSessionFactory.createUserSession(jwt));
        response.cookie("Authorization", jwt.getToken());
        response.redirect("/fitme/ui/dashboard");

        return "";
    };


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