package ar.edu.davinci.controller.user;

import ar.edu.davinci.controller.FitmeRouter;
import ar.edu.davinci.dao.routine.RoutineTemplateService;
import ar.edu.davinci.dao.user.UserEntityService;
import ar.edu.davinci.dao.user.detail.UserExperienceService;
import ar.edu.davinci.dao.user.detail.UserRoutineService;
import ar.edu.davinci.domain.FitmeRoles;
import ar.edu.davinci.domain.dto.fitme.routine.ListRoutineTemplateDTO;
import ar.edu.davinci.domain.dto.fitme.scoring.CoachTipDTO;
import ar.edu.davinci.domain.dto.fitme.user.UserInfoLightRequestDTO;
import ar.edu.davinci.domain.dto.fitme.user.UserInfoRequestDTO;
import ar.edu.davinci.domain.dto.fitme.user.UserSessionDTO;
import ar.edu.davinci.domain.model.routine.RoutineTemplate;
import ar.edu.davinci.domain.model.user.UserEntity;
import ar.edu.davinci.domain.model.user.detail.UserExperience;
import ar.edu.davinci.domain.model.user.detail.UserInfo;
import ar.edu.davinci.domain.model.user.detail.UserRoutine;
import ar.edu.davinci.infraestructure.security.session.UserSessionFactory;
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
import java.util.stream.Collectors;

import static ar.edu.davinci.infraestructure.security.SecurityHandler.authClient;
import static spark.Spark.*;

@Slf4j
public class UserEntityRouter extends FitmeRouter {

    private JsonTransformer jsonTransformer;
    private UserEntityService userEntityService;
    private UserSessionFactory userSessionFactory;
    private UserRoutineService userRoutineService;
    private UserExperienceService userExperienceService;
    private RoutineTemplateService routineTemplateService;

    @Inject
    public UserEntityRouter(Gson objectMapper,
                            UserEntityService userEntityService,
                            SessionFactory sessionFactory,
                            UserSessionFactory userSessionFactory,
                            UserRoutineService userRoutineService,
                            UserExperienceService userExperienceService,
                            RoutineTemplateService routineTemplateService,
                            JsonTransformer jsonTransformer) {
        super(objectMapper, sessionFactory);
        this.userEntityService = userEntityService;
        this.userRoutineService = userRoutineService;
        this.userSessionFactory = userSessionFactory;
        this.userExperienceService = userExperienceService;
        this.routineTemplateService = routineTemplateService;
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

            get("/:id_user/", getUser, jsonTransformer);
            get("/:id_user/light", getUserLight, jsonTransformer);

            get("/:id_user/user-routine", getListUserRoutines, jsonTransformer);
            put("/:id_user/user-routine", setUserRoutine, jsonTransformer);

            get("/:id_user/user-routine/:id_user_routine", getUserRoutine, jsonTransformer);
            get("/:id_user/user-routine/:id_user_routine/user-experience", getUserExperiencesFromUserRoutine, jsonTransformer);
            get("/:id_user/user-routine/:id_user_routine/user-experience/:id_user_experience", getUserExperienceFromUserRoutine, jsonTransformer);

            get("/:id_user/user-routine/:id_user_routine/user-experience/:id_user_experience", getUserExperienceFromUserRoutine, jsonTransformer);
            post("/:id_user/user-routine/:id_user_routine/user-experience/:id_user_experience", setUserExperienceFromUserRoutine, jsonTransformer);
            post("/:id_user/user-routine/:id_user_routine/user-experience/:id_user_experience/coach-tip", setCoachTipUserExperienceFromUserRoutine, jsonTransformer);

        };
    }

    private final Route getListUsers = doInTransaction(false, (Request request, Response response) ->
            userEntityService.findAll()
    );

    private final Route setUserRoutine = doInTransaction(true, (Request request, Response response) -> {
        ListRoutineTemplateDTO req = (ListRoutineTemplateDTO) jsonTransformer.asJson(request.body(), ListRoutineTemplateDTO.class);
        UserEntity userEntity = userEntityService.get(request.params("id_user"));

        for (Long id : req.getRoutine_template_ids()) {
            RoutineTemplate routineTemplate = routineTemplateService.get(id);
            UserRoutine userRoutine = userRoutineService.create(new UserRoutine(routineTemplate));
            userEntity.addUserRoutine(userRoutine);
        }

        userEntityService.update(userEntity);

        return "";
    });

    private final Route getUser = doInTransaction(true, (Request request, Response response) ->
            userEntityService.get(request.params("id_user"))
    );

    private final Route getUserLight = doInTransaction(true, (Request request, Response response) -> {
        UserEntity u = userEntityService.get(request.params("id_user"));
        UserInfo ui = u.getUserInfo();

        UserInfoRequestDTO userInfoRequestDTO = new UserInfoRequestDTO(
                ui.getInitialWeight(),
                ui.getHeight(),
                ui.getCurrentFat(),
                ui.getFrecuencyExercise(),
                ui.getUserGoal());

        UserInfoLightRequestDTO userResponse = new UserInfoLightRequestDTO(
                u.getId(),
                userInfoRequestDTO,
                u.getName(),
                u.getLastName(),
                u.getEmail(),
                u.getPicture(),
                u.getNickname(),
                u.getGenre(),
                u.getRole());

        return userResponse;
    });

    private final Route getListUserRoutines = doInTransaction(true, (Request request, Response response) ->
            userEntityService.get(request.params("id_user")).getUserRoutines()
    );

    private final Route getUserRoutine = doInTransaction(true, (Request request, Response response) ->
            getUserRoutineFromUser(request.params("id_user"), Long.parseLong(request.params("id_user_routine")))
    );

    private final Route getUserExperiencesFromUserRoutine = doInTransaction(true, (Request request, Response response) ->
    {
        UserRoutine userRoutine = getUserRoutineFromUser(request.params("id_user"), Long.parseLong(request.params("id_user_routine")));

        return userRoutine.getUserExperiences();
    });


    private final Route getUserExperienceFromUserRoutine = doInTransaction(true, (Request request, Response response) ->
    {
        UserRoutine userRoutine = getUserRoutineFromUser(request.params("id_user"), Long.parseLong(request.params("id_user_routine")));

        return getUserExperienceFromUserRoutine(userRoutine, Long.parseLong(request.params("id_user_experience")));
    });

    private final Route setUserExperienceFromUserRoutine = doInTransaction(true, (Request request, Response response) ->
    {
        UserRoutine userRoutine = getUserRoutineFromUser(request.params("id_user"), Long.parseLong(request.params("id_user_routine")));
        UserExperience req = (UserExperience) jsonTransformer.asJson(request.body(), UserExperience.class);

        userRoutine.addUserExperience(userExperienceService.create(req));
        userRoutineService.update(userRoutine);

        return "";
    });

    private final Route setCoachTipUserExperienceFromUserRoutine = doInTransaction(true, (Request request, Response response) ->
    {
        CoachTipDTO tip = (CoachTipDTO) jsonTransformer.asJson(request.body(), CoachTipDTO.class);

        UserRoutine userRoutine = getUserRoutineFromUser(request.params("id_user"), Long.parseLong(request.params("id_user_routine")));
        UserExperience userExperience = getUserExperienceFromUserRoutine(userRoutine, Long.parseLong(request.params("id_user_experience")));

        userExperience.setCoachTip(tip.getTip());

        userExperienceService.update(userExperience);

        return "";

    });

    private UserRoutine getUserRoutineFromUser(String userId, Long userRoutineId) {
        UserEntity userEntity = userEntityService.get(userId);

        return userEntity.getUserRoutines()
                .stream()
                .filter(ur -> ur.getId().equals(userRoutineId))
                .collect(Collectors.toList()).get(0);
    }

    private UserExperience getUserExperienceFromUserRoutine(UserRoutine userRoutine, Long userExperienceId) {

        return userRoutine.getUserExperiences()
                .stream()
                .filter(ur -> ur.getId().equals(userExperienceId))
                .collect(Collectors.toList()).get(0);
    }

    private final Route callbackSession = doInTransaction(true, (Request request, Response response) -> {

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

    private final Route createSession = doInTransaction(true, (Request request, Response response) -> {
        UserSessionDTO session = (UserSessionDTO) jsonTransformer.asJson(request.body(), UserSessionDTO.class);

        DecodedJWT jwt = JWT.decode(session.getToken_id());
        FitmeRoles role = Optional.of(
                FitmeRoles.valueOf(session.getRole())
        ).orElse(FitmeRoles.READONLY);

        userSessionFactory.createUserSession(jwt, role);

        return "";
    });


}
