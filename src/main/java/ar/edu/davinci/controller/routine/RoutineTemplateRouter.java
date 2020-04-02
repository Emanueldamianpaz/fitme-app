package ar.edu.davinci.controller.routine;

import ar.edu.davinci.controller.EnumResponse;
import ar.edu.davinci.controller.FitmeRouter;
import ar.edu.davinci.dao.routine.RoutineTemplateService;
import ar.edu.davinci.dao.routine.detail.MealNutritionService;
import ar.edu.davinci.dao.routine.detail.WorkoutExerciseService;
import ar.edu.davinci.domain.dto.ResponseBody;
import ar.edu.davinci.domain.dto.fitme.routineTemplate.RoutineTemplateRequestDTO;
import ar.edu.davinci.domain.model.routine.RoutineTemplate;
import ar.edu.davinci.domain.model.routine.detail.MealNutrition;
import ar.edu.davinci.domain.model.routine.detail.WorkoutExercise;
import ar.edu.davinci.domain.types.ScoringType;
import ar.edu.davinci.infraestructure.utils.JsonTransformer;
import com.github.racc.tscg.TypesafeConfig;
import com.google.gson.Gson;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.RouteGroup;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

import static spark.Spark.*;


public class RoutineTemplateRouter extends FitmeRouter {

    private String apiPath;
    private JsonTransformer jsonTransformer;
    private RoutineTemplateService routineTemplateService;
    private MealNutritionService mealNutritionService;
    private WorkoutExerciseService workoutExerciseService;

    @Inject
    public RoutineTemplateRouter(Gson objectMapper,
                                 RoutineTemplateService routineTemplateService,
                                 SessionFactory sessionFactory,
                                 JsonTransformer jsonTransformer,
                                 MealNutritionService mealNutritionService,
                                 WorkoutExerciseService workoutExerciseService,
                                 @TypesafeConfig("app.api") String apiPath) {
        super(objectMapper, sessionFactory);
        this.apiPath = apiPath;
        this.routineTemplateService = routineTemplateService;
        this.jsonTransformer = jsonTransformer;
        this.mealNutritionService = mealNutritionService;
        this.workoutExerciseService = workoutExerciseService;
    }

    @Override
    public String path() {
        return apiPath + "/routine-template";
    }

    @Override
    public RouteGroup routes() {
        return () -> {
            get("", getRoutineTemplates, jsonTransformer);
            //   get("/:id", getRoutineTemplate, jsonTransformer);
            post("", createRoutineTemplate, jsonTransformer);
            patch("/:id", updateRoutineTemplate, jsonTransformer);
            delete("/:id", deleteRoutineTemplate, jsonTransformer);
        };
    }


    private final Route getRoutineTemplates = doInTransaction(false, (Request request, Response response) ->
            routineTemplateService.findAll()
    );

    private final Route getRoutineTemplate = doInTransaction(false, (Request request, Response response) ->
            routineTemplateService.get(request.params("id"))
    );

    private final Route createRoutineTemplate = doInTransaction(true, (Request request, Response response) ->
            {
                RoutineTemplate routineTemplateRequest = (RoutineTemplate) jsonTransformer.asJson(request.body(), RoutineTemplate.class);

                Set<MealNutrition> mealNutritions = new HashSet<>();
                Set<WorkoutExercise> workoutExercises = new HashSet<>();

                for (MealNutrition ml : routineTemplateRequest.getMealNutritions())
                    mealNutritions.add(mealNutritionService.get(ml.getId()));

                for (WorkoutExercise we : routineTemplateRequest.getWorkoutExercises())
                    workoutExercises.add(workoutExerciseService.get(we.getId()));

                RoutineTemplate newRoutineTemplate = new RoutineTemplate(
                        routineTemplateRequest.getName(),
                        routineTemplateRequest.getDescription(),
                        ScoringType.UNKNOWN,
                        workoutExercises,
                        mealNutritions
                );

                return routineTemplateService.create(newRoutineTemplate);
            }
    );

    private final Route updateRoutineTemplate = doInTransaction(true, (Request request, Response response) ->
            {
                RoutineTemplateRequestDTO routineTemplateRequest = (RoutineTemplateRequestDTO) jsonTransformer.asJson(request.body(), RoutineTemplateRequestDTO.class);

                Set<MealNutrition> mealNutritions = new HashSet<>();
                Set<WorkoutExercise> workoutExercises = new HashSet<>();

                for (Long id : routineTemplateRequest.getNutritions()) {
                    mealNutritions.add(mealNutritionService.get(id));
                }

                for (Long id : routineTemplateRequest.getExercises()) {
                    workoutExercises.add(workoutExerciseService.get(id));
                }
                return routineTemplateService.update(new RoutineTemplate(Long.parseLong(request.params("id")), workoutExercises, mealNutritions));
            }
    );

    private final Route deleteRoutineTemplate = doInTransaction(true, (Request request, Response response) ->
            {
                routineTemplateService.delete(Long.parseLong(request.params("id")));
                return new ResponseBody(EnumResponse.DELETED.name(), "Rutina de ejemplo eliminada");
            }
    );


}
