package ar.edu.davinci.controller.training;

import ar.edu.davinci.controller.FitmeRouter;
import ar.edu.davinci.dao.training.TrainingSessionService;
import ar.edu.davinci.dao.user.detail.UserInfoService;
import ar.edu.davinci.domain.model.training.TrainingSession;
import ar.edu.davinci.domain.model.training.detail.ExerciseRunningSession;
import ar.edu.davinci.domain.model.user.detail.UserInfo;
import ar.edu.davinci.infraestructure.utils.JsonTransformer;
import com.github.racc.tscg.TypesafeConfig;
import com.google.gson.Gson;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.RouteGroup;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.UUID;

import static spark.Spark.get;
import static spark.Spark.post;

public class TrainingSessionRouter extends FitmeRouter {

    private String apiPath;
    private JsonTransformer jsonTransformer;
    private TrainingSessionService trainingSessionService;
    private UserInfoService userInfoService;

    @Inject
    public TrainingSessionRouter(Gson objectMapper,
                                 SessionFactory sessionFactory,
                                 @TypesafeConfig("app.api") String apiPath,
                                 JsonTransformer jsonTransformer,
                                 TrainingSessionService trainingSessionService,
                                 UserInfoService userInfoService
    ) {
        super(objectMapper, sessionFactory);
        this.apiPath = apiPath;
        this.userInfoService = userInfoService;
        this.trainingSessionService = trainingSessionService;
        this.jsonTransformer = jsonTransformer;
    }

    @Override
    public String path() {
        return apiPath + "/training-session";
    }

    @Override
    public RouteGroup routes() {
        return () -> {
            get("/:id_user/info", getExerciseSessions, jsonTransformer);
            post("/:id_user/exercise", addExerciseSession, jsonTransformer);
            //     put("/:id_user/exercise", updateExerciseSession, jsonTransformer);

        };
    }


    private final Route getExerciseSessions = doInTransaction(false, (Request request, Response response) ->
            userInfoService.get(request.params("id_user")).getTrainingSession()
    );

    private final Route addExerciseSession = doInTransaction(true, (Request request, Response response) ->
            {

                UserInfo userInfo = userInfoService.get(request.params("id_user"));
                ExerciseRunningSession exerciseRunningSessionRequest = (ExerciseRunningSession)
                        jsonTransformer.asJson(request.body(), ExerciseRunningSession.class);

                ExerciseRunningSession ersNew = new ExerciseRunningSession(
                        UUID.randomUUID().toString(),
                        new Timestamp(System.currentTimeMillis()),
                        exerciseRunningSessionRequest.getScoring(),
                        exerciseRunningSessionRequest.getRunningSession()
                );

                TrainingSession trainingSession = new TrainingSession();
                trainingSession.addExerciseRunningSession(ersNew);

                userInfo.addExerciseSession(trainingSessionService.create(trainingSession));
                return userInfoService.update(userInfo);
            }
    );
/*
    private final Route updateExerciseSession = doInTransaction(false, (Request request, Response response) ->
            {
                UserInfo userInfo = userInfoService.get(request.params("id_user"));

                ExerciseRunningSession exerciseRunningSession = new ExerciseRunningSession(
                        UUID.randomUUID().toString(),
                        new Timestamp(System.currentTimeMillis()),
                        ScoringType.UNKNOWN,
                        (RunningSession) jsonTransformer.asJson(request.body(), RunningSession.class)
                );

                TrainingSession trainingSession = new TrainingSession(jsonTransformer.render(exerciseRunningSession), "");
                trainingSessionService.create(trainingSession);

                userInfo.addExerciseSession(trainingSession);

                return userInfoService.update(userInfo);
            }
    );

    private final Route addNutritionSession = doInTransaction(true, (Request request, Response response) ->
            {

                String id = request.params("id");
                UserInfo userInfo = userInfoService.get(id);

                NutritionSession session = (NutritionSession) jsonTransformer.asJson(request.body(), NutritionSession.class);

                TrainingSession trainingSession = new TrainingSession("", jsonTransformer.render(session));
                trainingSessionService.create(trainingSession);
                userInfo.addExerciseSession(trainingSession);

                return userInfoService.update(userInfo);

            }
    );
*/
}
