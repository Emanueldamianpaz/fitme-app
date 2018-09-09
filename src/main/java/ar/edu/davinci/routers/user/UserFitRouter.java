package ar.edu.davinci.routers.user;

import ar.edu.davinci.domain.model.UserFit;
import ar.edu.davinci.dto.fitme.user.UserFitExerciseDTO;
import ar.edu.davinci.dto.fitme.user.UserFitNutritionDTO;
import ar.edu.davinci.routers.FitmeRouter;
import ar.edu.davinci.service.user.UserFitService;
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

public class UserFitRouter extends FitmeRouter {

    private String apiPath;
    private JsonTransformer jsonTransformer;
    private UserFitService userFitService;

    @Inject
    public UserFitRouter(Gson objectMapper,
                         UserFitService userFitService,
                         SessionFactory sessionFactory,
                         JsonTransformer jsonTransformer,
                         @TypesafeConfig("app.api") String apiPath) {
        super(objectMapper, sessionFactory);
        this.apiPath = apiPath;
        this.userFitService = userFitService;
        this.jsonTransformer = jsonTransformer;
    }

    @Override
    public String path() {
        return apiPath + "/user_fit";
    }

    @Override
    public RouteGroup routes() {
        return () -> {
            get("/:id", getUserFit, jsonTransformer);
            post("/:id/exercise", addExerciseInformation, jsonTransformer);
            post("/:id/nurition", addNutritionInformation, jsonTransformer);
        };
    }


    private final Route getUserFit = doInTransaction(false, (Request request, Response response) ->
            userFitService.get(Long.parseLong(request.params("id")))
    );

    private final Route addExerciseInformation = doInTransaction(false, (Request request, Response response) ->
            {
                // TODO Hacer un try catch en caso de que falle
                UserFitExerciseDTO userFitRequest = (UserFitExerciseDTO) jsonTransformer.asJson(request.body(), UserFitExerciseDTO.class);

                return userFitService.update(new UserFit(Long.parseLong(request.params("id")), jsonTransformer.render(userFitRequest), ""));
            }
    );

    private final Route addNutritionInformation = doInTransaction(true, (Request request, Response response) ->
            {
                // TODO Hacer un try catch en caso de que falle
                UserFitNutritionDTO userFitRequest = (UserFitNutritionDTO) jsonTransformer.asJson(request.body(), UserFitNutritionDTO.class);

                return userFitService.update(new UserFit(Long.parseLong(request.params("id")), "", jsonTransformer.render(userFitRequest)));

            }
    );

}