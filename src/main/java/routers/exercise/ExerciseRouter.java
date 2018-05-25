package routers.exercise;

import com.github.racc.tscg.TypesafeConfig;
import com.google.gson.Gson;
import dto.ResponseDTO;
import org.hibernate.SessionFactory;
import routers.FitmeRouter;
import service.exercise.ExerciseService;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.RouteGroup;
import utils.JsonTransformer;

import javax.inject.Inject;

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
            patch("", updateExercise, jsonTransformer);
            delete("", deleteExercise, jsonTransformer);
        };
    }


    private final Route getExercises = doInTransaction(false, (Request request, Response response) ->
            new ResponseDTO("OK", "Rutina creada!")
    );

    private final Route createExercise = doInTransaction(false, (Request request, Response response) ->
            new ResponseDTO(ExerciseResponse.ExerciseCreateOk.name(), "Rutina creada!")
    );

    private final Route updateExercise = doInTransaction(false, (Request request, Response response) ->
            new ResponseDTO(ExerciseResponse.ExerciseUpdateOk.name(), "Rutina modificada")
    );

    private final Route deleteExercise = doInTransaction(false, (Request request, Response response) ->
            new ResponseDTO(ExerciseResponse.ExerciseDeleteOk.name(), "Rutina eliminada")
    );


}
