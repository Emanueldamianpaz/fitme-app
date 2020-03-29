package ar.edu.davinci.controller.routine.detail;

import ar.edu.davinci.controller.EnumResponse;
import ar.edu.davinci.controller.FitmeRouter;
import ar.edu.davinci.dao.routine.detail.MealNutritionService;
import ar.edu.davinci.domain.dto.ResponseBody;
import ar.edu.davinci.domain.dto.fitme.nutrition.NutritionRequestDTO;
import ar.edu.davinci.domain.model.routine.detail.MealNutrition;
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


public class MealNutritionRouter extends FitmeRouter {

    private String apiPath;
    private JsonTransformer jsonTransformer;
    private MealNutritionService mealNutritionService;

    @Inject
    public MealNutritionRouter(Gson objectMapper,
                               MealNutritionService mealNutritionService,
                               SessionFactory sessionFactory,
                               JsonTransformer jsonTransformer,
                               @TypesafeConfig("app.api") String apiPath) {
        super(objectMapper, sessionFactory);
        this.apiPath = apiPath;
        this.mealNutritionService = mealNutritionService;
        this.jsonTransformer = jsonTransformer;
    }

    @Override
    public String path() {
        return apiPath + "/meal-nutrition";
    }

    @Override
    public RouteGroup routes() {
        return () -> {
            get("", getMealNutritions, jsonTransformer);
            get("/:id", getMealNutrition, jsonTransformer);

            post("", createMealNutrition, jsonTransformer);
            patch("/:id", updateMealNutrition, jsonTransformer);
            delete("/:id", deleteMealNutrition, jsonTransformer);
        };
    }


    private final Route getMealNutritions = doInTransaction(false, (Request request, Response response) ->
            mealNutritionService.findAll()
    );

    private final Route getMealNutrition = doInTransaction(false, (Request request, Response response) ->
            mealNutritionService.get(request.params("id"))
    );

    private final Route createMealNutrition = doInTransaction(true, (Request request, Response response) ->
            {
                NutritionRequestDTO nutritionRequest = (NutritionRequestDTO) jsonTransformer.asJson(request.body(), NutritionRequestDTO.class);
                return mealNutritionService.create(new MealNutrition(nutritionRequest));
            }
    );

    private final Route updateMealNutrition = doInTransaction(true, (Request request, Response response) ->
            {
                NutritionRequestDTO nutritionRequest = (NutritionRequestDTO) jsonTransformer.asJson(request.body(), NutritionRequestDTO.class);
                return mealNutritionService.update(new MealNutrition(Long.parseLong(request.params("id")), nutritionRequest));
            }
    );

    private final Route deleteMealNutrition = doInTransaction(true, (Request request, Response response) ->
            {
                mealNutritionService.delete(Long.parseLong(request.params("id")));
                return new ResponseBody(EnumResponse.DELETED.name(), "Dieta eliminada");
            }
    );
}
