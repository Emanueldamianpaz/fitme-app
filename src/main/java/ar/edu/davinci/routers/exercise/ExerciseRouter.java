package ar.edu.davinci.routers.exercise;

import ar.edu.davinci.domain.model.Exercise;
import ar.edu.davinci.dto.exercise.ExerciseRequestDTO;
import com.github.racc.tscg.TypesafeConfig;
import com.google.gson.Gson;
import ar.edu.davinci.dto.ResponseDTO;
import org.hibernate.SessionFactory;
import ar.edu.davinci.routers.FitmeRouter;
import ar.edu.davinci.service.exercise.ExerciseService;
import spark.*;
import ar.edu.davinci.utils.JsonTransformer;

import javax.inject.Inject;

import java.util.Optional;

import static spark.Spark.*;


public class ExerciseRouter extends FitmeRouter {

    private String apiPath;
    private JsonTransformer jsonTransformer;
    private ExerciseService exerciseService;

    @Inject
    public ExerciseRouter(Gson objectMapper,
                          ExerciseService exerciseService,
                          SessionFactory sessionFactory,
                          JsonTransformer jsonTransformer,
                          @TypesafeConfig("app.api") String apiPath) {
        super(objectMapper, sessionFactory);
        this.apiPath = apiPath;
        this.exerciseService = exerciseService;
        this.jsonTransformer = jsonTransformer;
    }

    @Override
    public String path() {
        return apiPath + "/exercise";
    }

    @Override
    public RouteGroup routes() {
        return () -> {
            get("", getExercises, jsonTransformer);
            post("", createExercise, jsonTransformer);
            patch("/:id", updateExercise, jsonTransformer);
            delete("/:id", deleteExercise, jsonTransformer);
        };
    }


    private final Route getExercises = doInTransaction(false, (Request request, Response response) ->
            exerciseService.findAll()
    );

    private final Route createExercise = doInTransaction(false, (Request request, Response response) ->
            {
                ExerciseRequestDTO exerciseRequest = (ExerciseRequestDTO) jsonTransformer.asJson(request.body(), ExerciseRequestDTO.class);
                return exerciseService.create(new Exercise(exerciseRequest));
            }
    );

    private final Route updateExercise = doInTransaction(false, (Request request, Response response) ->
            {
                ExerciseRequestDTO exerciseRequest = (ExerciseRequestDTO) jsonTransformer.asJson(request.body(), ExerciseRequestDTO.class);
                return exerciseService.update(new Exercise(Long.parseLong(request.params("id")), exerciseRequest));
            }
    );

    private final Route deleteExercise = doInTransaction(false, (Request request, Response response) ->
            {
                exerciseService.delete(Long.parseLong(request.params("id")));
                return new ResponseDTO(ExerciseResponse.ExerciseDeleteOk.name(), "Rutina eliminada");
            }
    );


}