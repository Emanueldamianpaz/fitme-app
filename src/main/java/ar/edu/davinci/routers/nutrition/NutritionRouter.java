package ar.edu.davinci.routers.nutrition;

import com.github.racc.tscg.TypesafeConfig;
import com.google.gson.Gson;
import ar.edu.davinci.dto.ResponseDTO;
import org.hibernate.SessionFactory;
import ar.edu.davinci.routers.FitmeRouter;
import ar.edu.davinci.service.nutrition.NutritionService;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.RouteGroup;
import ar.edu.davinci.utils.JsonTransformer;

import javax.inject.Inject;

import static spark.Spark.*;


public class NutritionRouter extends FitmeRouter {

    private String apiPath;
    private JsonTransformer jsonTransformer;
    private NutritionService nutritionService;

    @Inject
    public NutritionRouter(Gson objectMapper,
                           NutritionService nutritionService,
                           SessionFactory sessionFactory,
                           JsonTransformer jsonTransformer,
                           @TypesafeConfig("app.api") String apiPath) {
        super(objectMapper, sessionFactory);
        this.apiPath = apiPath;
        this.nutritionService = nutritionService;
        this.jsonTransformer = jsonTransformer;
    }

    @Override
    public String path() {
        return apiPath + "/nutrition";
    }

    @Override
    public RouteGroup routes() {
        return () -> {
            get("", getNutritions, jsonTransformer);
            post("", createNutrition, jsonTransformer);
            patch("", updateNutrition, jsonTransformer);
            delete("", deleteNutrition, jsonTransformer);
        };
    }


    private final Route getNutritions = doInTransaction(false, (Request request, Response response) ->
            new ResponseDTO("OK", "Rutina creada!")
    );

    private final Route createNutrition = doInTransaction(false, (Request request, Response response) ->
            new ResponseDTO(NutritionResponse.NutritionCreateOk.name(), "Rutina creada!")
    );

    private final Route updateNutrition = doInTransaction(false, (Request request, Response response) ->
            new ResponseDTO(NutritionResponse.NutritionUpdateOk.name(), "Rutina modificada")
    );

    private final Route deleteNutrition = doInTransaction(false, (Request request, Response response) ->
            new ResponseDTO(NutritionResponse.NutritionDeleteOk.name(), "Rutina eliminada")
    );


}
