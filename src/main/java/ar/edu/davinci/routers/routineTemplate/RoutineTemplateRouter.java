package ar.edu.davinci.routers.routineTemplate;

import ar.edu.davinci.domain.model.Exercise;
import ar.edu.davinci.domain.model.Nutrition;
import ar.edu.davinci.domain.model.RoutineTemplate;
import ar.edu.davinci.dto.ResponseDTO;
import ar.edu.davinci.dto.fitme.routineTemplate.RoutineTemplateRequestDTO;
import ar.edu.davinci.routers.EnumResponse;
import ar.edu.davinci.routers.FitmeRouter;
import ar.edu.davinci.service.exercise.ExerciseService;
import ar.edu.davinci.service.nutrition.NutritionService;
import ar.edu.davinci.service.routineTemplate.RoutineTemplateService;
import ar.edu.davinci.utils.JsonTransformer;
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

                Set<Nutrition> nutritions = new HashSet<>();
                Set<Exercise> exercises = new HashSet<>();

                for (String id : routineTemplateRequest.getNutritions()) {
                    nutritions.add(nutritionService.get(id));
                }

                for (String id : routineTemplateRequest.getExercises()) {
                    exercises.add(exerciseService.get(id));
                }


                String scoring = "n/a";

                return routineTemplateService.create(new RoutineTemplate(exercises, nutritions, scoring));
            }
    );

    private final Route updateRoutineTemplate = doInTransaction(true, (Request request, Response response) ->
            {
                RoutineTemplateRequestDTO routineTemplateRequest = (RoutineTemplateRequestDTO) jsonTransformer.asJson(request.body(), RoutineTemplateRequestDTO.class);

                Set<Nutrition> nutritions = new HashSet<>();
                Set<Exercise> exercises = new HashSet<>();

                for (String id : routineTemplateRequest.getNutritions()) {
                    nutritions.add(nutritionService.get(id));
                }

                for (String id : routineTemplateRequest.getExercises()) {
                    exercises.add(exerciseService.get(id));
                }
                return routineTemplateService.update(new RoutineTemplate(Long.parseLong(request.params("id")), exercises, nutritions));
            }
    );

    private final Route deleteRoutineTemplate = doInTransaction(true, (Request request, Response response) ->
            {
                routineTemplateService.delete(Long.parseLong(request.params("id")));
                return new ResponseDTO(EnumResponse.DeleteOk.name(), "Rutina de ejemplo eliminada");
            }
    );


}
