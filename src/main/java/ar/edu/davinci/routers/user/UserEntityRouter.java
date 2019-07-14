package ar.edu.davinci.routers.user;

import ar.edu.davinci.domain.model.User;
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
import javax.servlet.http.Cookie;

import static ar.edu.davinci.infraestructure.security.SecurityFilter.authClient;
import static spark.Spark.*;

@Slf4j
public class UserEntityRouter extends FitmeRouter {

    private JsonTransformer jsonTransformer;
    private UserEntityService userEntityService;

    @Inject
    public UserEntityRouter(Gson objectMapper,
                            UserEntityService userEntityService,
                            SessionFactory sessionFactory,
                            JsonTransformer jsonTransformer) {
        super(objectMapper, sessionFactory);
        this.userEntityService = userEntityService;
        this.jsonTransformer = jsonTransformer;
    }

    @Override
    public String path() {
        return "/user";
    }

    @Override
    public RouteGroup routes() {
        return () -> {
            get("", getListUsers, jsonTransformer);
            get("/callback", createSession, jsonTransformer);

            get("/:id/info", getUser, jsonTransformer);

        };
    }


    private final Route getListUsers = doInTransaction(false, (Request request, Response response) ->
            userEntityService.findAll()
    );

    private final Route getUser = doInTransaction(true, (Request request, Response response) ->
            userEntityService.get(Long.parseLong(request.params("id")))
    );

    private final Route createSession = (Request request, Response response) ->
    {
        log.info("Initializing Modules...");

        Tokens tokens = authClient.handle(request.raw());
        SessionUtils.set(request.raw(), "accessToken", tokens.getAccessToken());
        SessionUtils.set(request.raw(), "idToken", tokens.getIdToken());

        DecodedJWT jwt = JWT.decode(tokens.getIdToken());

        FitmeUser user = FitmeUser.builder()
                .id(jwt.getSubject())
                .name(jwt.getClaim("given_name").asString())
                .last_name(jwt.getClaim("family_name").asString())
                .picture(jwt.getClaim("picture").asString())
                .gender(jwt.getClaim("gender").asString())
                .nickname(jwt.getClaim("nickname").asString())
                .email(jwt.getClaim("email").asString())
                .build();

        if (userEntityService.get(Long.parseLong(request.params("id"))) == null) {
            userEntityService.create(new User(user));
        }

        // TODO Esto debería devolver un accessToken
        response.header("Authorization", jwt.getToken());
        response.raw().addCookie(new Cookie("Authorization", jwt.getToken()));
        response.cookie("/fitme", "fitme_session", jwt.getToken(), 3600, false, false);
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