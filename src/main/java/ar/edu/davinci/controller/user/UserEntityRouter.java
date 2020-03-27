package ar.edu.davinci.controller.user;

import ar.edu.davinci.domain.model.user.UserEntity;
import ar.edu.davinci.domain.model.user.detail.UserInfo;
import ar.edu.davinci.domain.model.user.detail.UserRoutine;
import ar.edu.davinci.domain.dto.fitme.routine.ListRoutineTemplateDTO;
import ar.edu.davinci.domain.dto.fitme.scoring.TipRequestDTO;
import ar.edu.davinci.domain.dto.fitme.user.UserInfoLightRequestDTO;
import ar.edu.davinci.domain.dto.fitme.user.UserInfoRequestDTO;
import ar.edu.davinci.domain.dto.fitme.user.UserSessionDTO;
import ar.edu.davinci.infraestructure.exception.FitmeException;
import ar.edu.davinci.domain.FitmeRoles;
import ar.edu.davinci.infraestructure.security.session.UserSessionFactory;
import ar.edu.davinci.controller.FitmeRouter;
import ar.edu.davinci.dao.routine.RoutineService;
import ar.edu.davinci.dao.user.UserEntityService;
import ar.edu.davinci.infraestructure.utils.JsonTransformer;
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
import java.util.Optional;

import static ar.edu.davinci.infraestructure.security.SecurityHandler.authClient;
import static spark.Spark.*;

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
            get("/callback", callbackSession, jsonTransformer); // Only for Admin/Coach/Web
            post("/session", createSession, jsonTransformer); // For all users(normally fitness_users)

            get("/:id/info", getUser, jsonTransformer);
            get("/:id/info/light", getUserLight, jsonTransformer);
            get("/:id/info/tip", getUserTip, jsonTransformer);


            get("/:id/info/routines", getUserRoutines, jsonTransformer);


            post("/:id/message", sendMessage, jsonTransformer);
            put("/:id/routines", setRoutines, jsonTransformer);

        };
    }

    private final Route getListUsers = doInTransaction(false, (Request request, Response response) ->
            userEntityService.findAll()
    );

    private final Route setRoutines = doInTransaction(true, (Request request, Response response) ->
    {
        ListRoutineTemplateDTO req = (ListRoutineTemplateDTO) jsonTransformer.asJson(request.body(), ListRoutineTemplateDTO.class);
        UserEntity userEntity = userEntityService.get(request.params("id"));

        for (Long id : req.getRoutine_template_ids()) {

            new UserRoutine()
            userRoutine.addRoutine(routineService.get(id));
        }

        UserRoutine userRoutine = new UserRoutine()
        for (Long id : req.getRoutine_template_ids()) {
            userRoutine.addRoutine(routineService.get(id));
        }
        userEntity.setUserRoutine(userRoutine);
        userEntityService.update(userEntity);

        return "";
    });

    private final Route sendMessage = doInTransaction(true, (Request request, Response response) ->
    {
        TipRequestDTO tip = (TipRequestDTO) jsonTransformer.asJson(request.body(), TipRequestDTO.class);

        UserEntity userEntity = userEntityService.get(request.params("id"));
        UserRoutine userRoutine = userEntity.getUserRoutine();

        if (userRoutine == null) {
            throw new FitmeException("User not active because, user haven't associated routine");
        }

        userRoutine.getUserExperience().setCoachTip(tip.getTip());

        userEntity.setUserRoutine(userRoutine);

        userEntityService.update(userEntity);

        return "";
    });


    private final Route getUser = doInTransaction(true, (Request request, Response response) ->
            userEntityService.get(request.params("id"))
    );

    private final Route getUserLight = doInTransaction(true, (Request request, Response response) -> {
                UserEntity u = userEntityService.get(request.params("id"));
                UserInfo ui = u.getUserInfo();

                UserInfoRequestDTO userInfoRequestDTO = new UserInfoRequestDTO(ui.getInitialWeight(), ui.getHeight(),
                        ui.getCurrentFat(), ui.getFrecuencyExercise(), ui.getUserGoal());

                UserInfoLightRequestDTO userResponse = new UserInfoLightRequestDTO(u.getId(), userInfoRequestDTO,
                        u.getName(), u.getLastName(), u.getEmail(), u.getPicture(), u.getNickname(), u.getGenre(),
                        u.getRole());

                return userResponse;
            }
    );

    private final Route getUserTip = doInTransaction(true, (Request request, Response response) -> {
                UserEntity u = userEntityService.get(request.params("id"));
                return new TipRequestDTO(u.getUserRoutine().getUserExperience().getCoachTip());
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
            SessionUtils.set(request.raw(), "accessToken", tokens.getAccessToken());
            SessionUtils.set(request.raw(), "idToken", tokens.getIdToken());

            DecodedJWT jwt = JWT.decode(tokens.getIdToken());

            userSessionFactory.createUserSession(jwt, FitmeRoles.COACH);

            response.header("Authorization", jwt.getToken());
            response.raw().addCookie(new Cookie("Authorization", jwt.getToken()));
            response.cookie("/fitme", "fitme_session", jwt.getToken(), 3600, false, false);
            response.redirect("/fitme/ui/dashboard");
        } catch (IdentityVerificationException | NullPointerException e) {
            log.error("Error handling auth", e);
            response.redirect("/fitme/ui/404");

        }


        return "";
    });

    private final Route createSession = doInTransaction(true, (Request request, Response response) ->
    {
        UserSessionDTO session = (UserSessionDTO) jsonTransformer.asJson(request.body(), UserSessionDTO.class);

        DecodedJWT jwt = JWT.decode(session.getToken_id());
        FitmeRoles role = Optional.of(
                FitmeRoles.valueOf(session.getRole())
        ).orElse(FitmeRoles.READONLY);

        userSessionFactory.createUserSession(jwt, role);

        return "";
    });


}
