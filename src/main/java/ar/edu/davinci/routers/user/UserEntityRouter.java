package ar.edu.davinci.routers.user;

import ar.edu.davinci.domain.model.Routine;
import ar.edu.davinci.domain.model.User;
import ar.edu.davinci.domain.model.UserInfo;
import ar.edu.davinci.domain.model.UserRoutine;
import ar.edu.davinci.dto.fitme.routine.SetRoutineRequestDTO;
import ar.edu.davinci.dto.fitme.scoring.TipRequestDTO;
import ar.edu.davinci.dto.fitme.user.UserInfoLightRequestDTO;
import ar.edu.davinci.dto.fitme.user.UserInfoRequestDTO;
import ar.edu.davinci.exception.FitmeException;
import ar.edu.davinci.infraestructure.security.session.UserSessionFactory;
import ar.edu.davinci.routers.FitmeRouter;
import ar.edu.davinci.service.routine.RoutineService;
import ar.edu.davinci.service.user.UserEntityService;
import ar.edu.davinci.utils.JsonTransformer;
import com.auth0.IdentityVerificationException;
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

import java.util.HashSet;
import java.util.Set;

import static ar.edu.davinci.infraestructure.security.filters.SecurityFilter.authClient;
import static spark.Spark.get;
import static spark.Spark.post;

@Slf4j
public class UserEntityRouter extends FitmeRouter {

    private JsonTransformer jsonTransformer;
    private UserEntityService userEntityService;
    private UserSessionFactory userSessionFactory;
    private RoutineService routineService;

    @Inject
    public UserEntityRouter(Gson objectMapper,
                            UserEntityService userEntityService,
                            SessionFactory sessionFactory,
                            UserSessionFactory userSessionFactory,
                            RoutineService routineService,
                            JsonTransformer jsonTransformer) {
        super(objectMapper, sessionFactory);
        this.userEntityService = userEntityService;
        this.routineService = routineService;
        this.userSessionFactory = userSessionFactory;
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
            get("/session", createSession, jsonTransformer); // For all users(normally fitness_users)
            get("/callback", callbackSession, jsonTransformer); // Only for Admin/Coach/Web

            get("/:id/info", getUser, jsonTransformer);
            get("/:id/info/light", getUserLight, jsonTransformer);

            get("/:id/info/routines", getUserRoutines, jsonTransformer);


            post("/:id/message", sendMessage, jsonTransformer);
            post("/:id/routines", setRoutines, jsonTransformer);

        };
    }

    private final Route getListUsers = doInTransaction(false, (Request request, Response response) ->
            userEntityService.findAll()
    );

    private final Route setRoutines = doInTransaction(true, (Request request, Response response) ->
    {
        SetRoutineRequestDTO req = (SetRoutineRequestDTO) jsonTransformer.asJson(request.body(), SetRoutineRequestDTO.class);
        User user = userEntityService.get(request.params("id"));

        Set<Routine> routines = new HashSet<>();
        for (Long id : req.getRoutines()) {
            Routine routine = routineService.get(id);
            routines.add(routine);
        }

        UserRoutine userRoutine = user.getUserRoutine();
        userRoutine.addRoutine(routines);


        user.setUserRoutine(userRoutine);
        userEntityService.update(user);

        return "";
    });

    private final Route sendMessage = doInTransaction(true, (Request request, Response response) ->
    {
        TipRequestDTO tip = (TipRequestDTO) jsonTransformer.asJson(request.body(), TipRequestDTO.class);

        User user = userEntityService.get(request.params("id"));
        UserRoutine userRoutine = user.getUserRoutine();

        if (userRoutine == null) {
            throw new FitmeException("User not active because, user haven't associated routine");
        }

        userRoutine.getScoring().setTip(tip.getTip());

        user.setUserRoutine(userRoutine);

        userEntityService.update(user);

        return "";
    });


    private final Route getUser = doInTransaction(true, (Request request, Response response) ->
            userEntityService.get(request.params("id"))
    );

    private final Route getUserLight = doInTransaction(true, (Request request, Response response) -> {
                User u = userEntityService.get(request.params("id"));
                UserInfo ui = u.getUserInfo();

                UserInfoRequestDTO userInfoRequestDTO = new UserInfoRequestDTO(ui.getInitialWeight(), ui.getHeight(),
                        ui.getCurrentFat(), ui.getFrecuencyExercise());

                UserInfoLightRequestDTO userResponse = new UserInfoLightRequestDTO(u.getId(), userInfoRequestDTO,
                        u.getName(), u.getLastName(), u.getEmail(), u.getPicture(), u.getNickname(), u.getGenre(),
                        u.getRole());

                return userResponse;
            }
    );
    private final Route getUserRoutines = doInTransaction(true, (Request request, Response response) ->
            userEntityService.get(request.params("id")).getUserRoutine()
    );

    private final Route callbackSession = doInTransaction(true, (Request request, Response response) ->
    {
        Tokens tokens = null;
        try {
            tokens = authClient.handle(request.raw());
        } catch (IdentityVerificationException e) {
            log.error("Error handling auth", e);
        }

        SessionUtils.set(request.raw(), "accessToken", tokens.getAccessToken());
        SessionUtils.set(request.raw(), "idToken", tokens.getIdToken());

        DecodedJWT jwt = JWT.decode(tokens.getIdToken());

        userSessionFactory.createUserSession(jwt);

        response.header("Authorization", jwt.getToken());
        response.raw().addCookie(new Cookie("Authorization", jwt.getToken()));
        response.cookie("/fitme", "fitme_session", jwt.getToken(), 3600, false, false);
        response.redirect("/fitme/ui/dashboard");

        return "";
    });

    private final Route createSession = doInTransaction(true, (Request request, Response response) ->
    {

        DecodedJWT jwt = JWT.decode(request.body());

        userSessionFactory.createUserSession(jwt);

        return "";
    });


}
