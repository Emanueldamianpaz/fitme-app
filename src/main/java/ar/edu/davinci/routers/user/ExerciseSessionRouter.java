package ar.edu.davinci.routers.user;

import ar.edu.davinci.domain.model.ExerciseSession;
import ar.edu.davinci.domain.model.UserInfo;
import ar.edu.davinci.dto.fitme.user.ExerciseSessionExerciseDTO;
import ar.edu.davinci.dto.fitme.user.ExerciseSessionNutritionDTO;
import ar.edu.davinci.routers.FitmeRouter;
import ar.edu.davinci.service.user.ExerciseSessionService;
import ar.edu.davinci.service.user.UserInfoService;
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

public class ExerciseSessionRouter extends FitmeRouter {

    private String apiPath;
    private JsonTransformer jsonTransformer;
    private ExerciseSessionService exerciseSessionService;
    private UserInfoService userInfoService;

    @Inject
    public ExerciseSessionRouter(Gson objectMapper,
                                 ExerciseSessionService exerciseSessionService,
                                 SessionFactory sessionFactory,
                                 JsonTransformer jsonTransformer,
                                 UserInfoService userInfoService,
                                 @TypesafeConfig("app.api") String apiPath) {
        super(objectMapper, sessionFactory);
        this.apiPath = apiPath;
        this.userInfoService = userInfoService;
        this.exerciseSessionService = exerciseSessionService;
        this.jsonTransformer = jsonTransformer;
    }

    @Override
    public String path() {
        return apiPath + "/exercise_session";
    }

    @Override
    public RouteGroup routes() {
        return () -> {
            get("/:id/info", getExerciseSessions, jsonTransformer);
            post("/:id/exercise", addExerciseSession, jsonTransformer);
            post("/:id/nurition", addNutritionSession, jsonTransformer);
        };
    }


    private final Route getExerciseSessions = doInTransaction(false, (Request request, Response response) ->
            userInfoService.get(request.params("id")).getExerciseSession()
    );

    private final Route addExerciseSession = doInTransaction(false, (Request request, Response response) ->
            {

                String id = request.params("id");
                UserInfo userInfo = userInfoService.get(id);

                ExerciseSessionExerciseDTO session = (ExerciseSessionExerciseDTO) jsonTransformer.asJson(request.body(), ExerciseSessionExerciseDTO.class);

                ExerciseSession exerciseSession = new ExerciseSession(jsonTransformer.render(session), "");
                exerciseSessionService.create(exerciseSession);

                userInfo.addExerciseSession(exerciseSession);

                return userInfoService.update(userInfo);
            }
    );

    private final Route addNutritionSession = doInTransaction(true, (Request request, Response response) ->
            {

                String id = request.params("id");
                UserInfo userInfo = userInfoService.get(id);

                ExerciseSessionNutritionDTO session = (ExerciseSessionNutritionDTO) jsonTransformer.asJson(request.body(), ExerciseSessionNutritionDTO.class);

                ExerciseSession exerciseSession = new ExerciseSession("", jsonTransformer.render(session));
                exerciseSessionService.create(exerciseSession);
                userInfo.addExerciseSession(exerciseSession);

                return userInfoService.update(userInfo);

            }
    );

}
