package ar.edu.davinci.controller.training;

import ar.edu.davinci.controller.FitmeRouter;
import ar.edu.davinci.dao.training.TrainingSessionService;
import ar.edu.davinci.dao.user.detail.UserInfoService;
import ar.edu.davinci.domain.model.training.TrainingSession;
import ar.edu.davinci.domain.model.training.detail.ExerciseRunningSession;
import ar.edu.davinci.domain.model.training.detail.NutritionSession;
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
import javax.persistence.NoResultException;
import java.util.Date;

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
            post("/:id_user/nutrition", addNutritionSession, jsonTransformer);
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

                ExerciseRunningSession ersNew = new ExerciseRunningSession(exerciseRunningSessionRequest);

                try {
                    TrainingSession trainingSessionFromToday = trainingSessionService.findByDate(new Date());
                    trainingSessionFromToday.addExerciseRunningSession(ersNew);
                    userInfo.addExerciseSession(trainingSessionService.create(trainingSessionFromToday));
                } catch (NoResultException e) {
                    TrainingSession trainingSessionNew = new TrainingSession();
                    trainingSessionNew.addExerciseRunningSession(ersNew);
                    userInfo.addExerciseSession(trainingSessionService.create(trainingSessionNew));
                }

                return userInfoService.update(userInfo);
            }
    );


    private final Route addNutritionSession = doInTransaction(true, (Request request, Response response) ->
            {
                UserInfo userInfo = userInfoService.get(request.params("id_user"));
                NutritionSession nutritionSessionRequest = (NutritionSession)
                        jsonTransformer.asJson(request.body(), NutritionSession.class);

                NutritionSession nsNew = new NutritionSession(nutritionSessionRequest);

                try {
                    TrainingSession trainingSessionFromToday = trainingSessionService.findByDate(new Date());
                    trainingSessionFromToday.addNutritionSession(nsNew);
                    userInfo.addExerciseSession(trainingSessionService.create(trainingSessionFromToday));
                } catch (NoResultException e) {
                    TrainingSession trainingSessionNew = new TrainingSession();
                    trainingSessionNew.addNutritionSession(nsNew);
                    userInfo.addExerciseSession(trainingSessionService.create(trainingSessionNew));
                }

                return userInfoService.update(userInfo);

            }
    );
}
