package ar.edu.davinci.controller.routine.detail;

import ar.edu.davinci.controller.EnumResponse;
import ar.edu.davinci.controller.FitmeRouter;
import ar.edu.davinci.dao.routine.detail.WorkoutExerciseService;
import ar.edu.davinci.domain.dto.ResponseBody;
import ar.edu.davinci.domain.dto.fitme.exercise.ExerciseRequestDTO;
import ar.edu.davinci.domain.model.routine.detail.WorkoutExercise;
import ar.edu.davinci.infraestructure.utils.JsonTransformer;
import com.github.racc.tscg.TypesafeConfig;
import com.google.gson.Gson;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.RouteGroup;

import javax.inject.Inject;

import static spark.Spark.*;

public class WorkoutExerciseRouter extends FitmeRouter {

    private String apiPath;
    private JsonTransformer jsonTransformer;
    private WorkoutExerciseService workoutExerciseService;

    @Inject
    public WorkoutExerciseRouter(Gson objectMapper,
                                 SessionFactory sessionFactory,
                                 JsonTransformer jsonTransformer,
                                 @TypesafeConfig("app.api") String apiPath,
                                 WorkoutExerciseService workoutExerciseService
    ) {
        super(objectMapper, sessionFactory);
        this.apiPath = apiPath;
        this.workoutExerciseService = workoutExerciseService;
        this.jsonTransformer = jsonTransformer;
    }

    @Override
    public String path() {
        return apiPath + "/workout-exercise";
    }

    @Override
    public RouteGroup routes() {
        return () -> {
            get("", getWorkoutExercises, jsonTransformer);
            get("/:id", getExercise, jsonTransformer);

            post("", createExercise, jsonTransformer);
            patch("/:id", updateExercise, jsonTransformer);
            delete("/:id", deleteExercise, jsonTransformer);
        };
    }

    private final Route getWorkoutExercises = doInTransaction(false, (Request request, Response response) ->
            workoutExerciseService.findAll()
    );

    private final Route getExercise = doInTransaction(false, (Request request, Response response) ->
            workoutExerciseService.get(request.params("id"))
    );

    private final Route createExercise = doInTransaction(true, (Request request, Response response) ->
            {
                ExerciseRequestDTO exerciseRequest = (ExerciseRequestDTO) jsonTransformer.asJson(request.body(), ExerciseRequestDTO.class);
                return workoutExerciseService.create(new WorkoutExercise(exerciseRequest));
            }
    );

    private final Route updateExercise = doInTransaction(true, (Request request, Response response) ->
            {
                ExerciseRequestDTO exerciseRequest = (ExerciseRequestDTO) jsonTransformer.asJson(request.body(), ExerciseRequestDTO.class);
                return workoutExerciseService.update(new WorkoutExercise(Long.parseLong(request.params("id")), exerciseRequest));
            }
    );

    private final Route deleteExercise = doInTransaction(true, (Request request, Response response) ->
            {
                workoutExerciseService.delete(Long.parseLong(request.params("id")));
                return new ResponseBody(EnumResponse.DELETED.name(), "Ejercicio eliminada");
            }
    );
}
