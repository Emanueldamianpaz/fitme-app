package routers.routine;

import com.github.racc.tscg.TypesafeConfig;
import routers.FitmeRouter;
import spark.Route;
import spark.RouteGroup;
import utils.JsonTransformer;

import javax.inject.Inject;

import static spark.Spark.get;


public class RoutineRouter extends FitmeRouter {

    private final String apiPath;
    JsonTransformer jsonTransformer;

    @Inject
    public RoutineRouter(JsonTransformer jsonTransformer,
                         @TypesafeConfig("app.api") String apiPath) {
        super(null, null);
        this.apiPath = apiPath;
        this.jsonTransformer = jsonTransformer;
    }

    private final Route getRoutines = (request, response) -> "ok";

    @Override
    public RouteGroup routes() {
        return () -> {
            get("", getRoutines, jsonTransformer);
        };
    }

    @Override
    public String path() {
        return apiPath + "/routine";
    }
}
