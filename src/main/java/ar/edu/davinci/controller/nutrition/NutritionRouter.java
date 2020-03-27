package ar.edu.davinci.controller.nutrition;

import ar.edu.davinci.domain.model.routine.detail.MealNutrition;
import ar.edu.davinci.domain.dto.ResponseBody;
import ar.edu.davinci.domain.dto.fitme.nutrition.NutritionRequestDTO;
import ar.edu.davinci.controller.EnumResponse;
import ar.edu.davinci.controller.FitmeRouter;
import ar.edu.davinci.dao.nutrition.NutritionService;
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
            nutritionService.get(request.params("id"))
    );

    private final Route createNutrition = doInTransaction(true, (Request request, Response response) ->
            {
                NutritionRequestDTO nutritionRequest = (NutritionRequestDTO) jsonTransformer.asJson(request.body(), NutritionRequestDTO.class);
                return nutritionService.create(new MealNutrition(nutritionRequest));
            }
    );

    private final Route updateNutrition = doInTransaction(true, (Request request, Response response) ->
            {
                NutritionRequestDTO nutritionRequest = (NutritionRequestDTO) jsonTransformer.asJson(request.body(), NutritionRequestDTO.class);
                return nutritionService.update(new MealNutrition(Long.parseLong(request.params("id")), nutritionRequest));
            }
    );

    private final Route deleteNutrition = doInTransaction(true, (Request request, Response response) ->
            {
                nutritionService.delete(Long.parseLong(request.params("id")));
                return new ResponseBody(EnumResponse.DELETED.name(), "Dieta eliminada");
            }
    );
}
