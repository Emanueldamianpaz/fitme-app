package ar.edu.davinci.controller.routineTemplate;

import ar.edu.davinci.domain.model.routine.RoutineTemplate;
import ar.edu.davinci.domain.model.routine.detail.MealNutrition;
import ar.edu.davinci.domain.model.routine.detail.WorkoutExercise;
import ar.edu.davinci.domain.types.ScoringType;
import ar.edu.davinci.domain.dto.ResponseBody;
import ar.edu.davinci.domain.dto.fitme.routineTemplate.RoutineTemplateRequestDTO;
import ar.edu.davinci.controller.EnumResponse;
import ar.edu.davinci.controller.FitmeRouter;
import ar.edu.davinci.dao.exercise.ExerciseService;
import ar.edu.davinci.dao.nutrition.NutritionService;
import ar.edu.davinci.dao.routineTemplate.RoutineTemplateService;
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
    private NutritionService nutritionService;
    private ExerciseService exerciseService;

    @Inject
    public RoutineTemplateRouter(Gson objectMapper,
                                 RoutineTemplateService routineTemplateService,
                                 SessionFactory sessionFactory,
                                 JsonTransformer jsonTransformer,
                                 NutritionService nutritionService,
                                 ExerciseService exerciseService,
                                 @TypesafeConfig("app.api") String apiPath) {
        super(objectMapper, sessionFactory);
        this.apiPath = apiPath;
        this.routineTemplateService = routineTemplateService;
        this.jsonTransformer = jsonTransformer;
        this.nutritionService = nutritionService;
        this.exerciseService = exerciseService;
    }

    @Override
    public String path() {
        return apiPath + "/routine_template";
    }

    @Override
    public RouteGroup routes() {
        return () -> {
            get("", getRoutineTemplates, jsonTransformer);
            get("/:id", getRoutineTemplate, jsonTransformer);
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
                RoutineTemplateRequestDTO routineTemplateRequest = (RoutineTemplateRequestDTO) jsonTransformer.asJson(request.body(), RoutineTemplateRequestDTO.class);

                Set<MealNutrition> mealNutritions = new HashSet<>();
                Set<WorkoutExercise> workoutExercises = new HashSet<>();

                for (Long id : routineTemplateRequest.getNutritions()) {
                    mealNutritions.add(nutritionService.get(id));
                }

                for (Long id : routineTemplateRequest.getExercises()) {
                    workoutExercises.add(exerciseService.get(id));
                }

                return routineTemplateService.create(new RoutineTemplate(workoutExercises, mealNutritions, ScoringType.UNKNOWN));
            }
    );

    private final Route updateRoutineTemplate = doInTransaction(true, (Request request, Response response) ->
            {
                RoutineTemplateRequestDTO routineTemplateRequest = (RoutineTemplateRequestDTO) jsonTransformer.asJson(request.body(), RoutineTemplateRequestDTO.class);

                Set<MealNutrition> mealNutritions = new HashSet<>();
                Set<WorkoutExercise> workoutExercises = new HashSet<>();

                for (Long id : routineTemplateRequest.getNutritions()) {
                    mealNutritions.add(nutritionService.get(id));
                }

                for (Long id : routineTemplateRequest.getExercises()) {
                    workoutExercises.add(exerciseService.get(id));
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
