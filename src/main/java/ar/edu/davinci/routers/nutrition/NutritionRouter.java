package ar.edu.davinci.routers.nutrition;

import ar.edu.davinci.domain.model.Nutrition;
import ar.edu.davinci.dto.fitme.nutrition.NutritionRequestDTO;
import ar.edu.davinci.routers.EnumResponse;
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
            get("/:id", getNutrition, jsonTransformer);
            post("", createNutrition, jsonTransformer);
            patch("/:id", updateNutrition, jsonTransformer);
            delete("/:id", deleteNutrition, jsonTransformer);
        };
    }


    private final Route getNutritions = doInTransaction(false, (Request request, Response response) ->
            nutritionService.findAll()
    );

    private final Route getNutrition = doInTransaction(false, (Request request, Response response) ->
            nutritionService.get(Long.parseLong(request.params("id")))
    );

    private final Route createNutrition = doInTransaction(true, (Request request, Response response) ->
            {
                NutritionRequestDTO nutritionRequest = (NutritionRequestDTO) jsonTransformer.asJson(request.body(), NutritionRequestDTO.class);
                return nutritionService.create(new Nutrition(nutritionRequest));
            }
    );

    private final Route updateNutrition = doInTransaction(true, (Request request, Response response) ->
            {
                NutritionRequestDTO nutritionRequest = (NutritionRequestDTO) jsonTransformer.asJson(request.body(), NutritionRequestDTO.class);
                return nutritionService.update(new Nutrition(Long.parseLong(request.params("id")), nutritionRequest));
            }
    );

    private final Route deleteNutrition = doInTransaction(true, (Request request, Response response) ->
            {
                nutritionService.delete(Long.parseLong(request.params("id")));
                return new ResponseDTO(EnumResponse.DeleteOk.name(), "Dieta eliminada");
            }
    );
}
