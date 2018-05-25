package routers.routine;

import com.github.racc.tscg.TypesafeConfig;
import com.google.gson.Gson;
import org.hibernate.SessionFactory;
import routers.FitmeRouter;
import service.routine.RoutineService;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.RouteGroup;
import utils.JsonTransformer;

import javax.inject.Inject;

import static spark.Spark.get;


public class RoutineRouter extends FitmeRouter {

    private final String apiPath;
    private JsonTransformer jsonTransformer;
    private RoutineService routineService;

    @Inject
    public RoutineRouter(Gson objectMapper,
                         RoutineService routineService,
                         SessionFactory sessionFactory,
                         JsonTransformer jsonTransformer,
                         @TypesafeConfig("app.api") String apiPath) {
        super(objectMapper, sessionFactory);
        this.apiPath = apiPath;
        this.routineService = routineService;
        this.jsonTransformer = jsonTransformer;
    }

    private final Route getRoutines = doInTransaction(false, (Request request, Response response) ->
            routineService.findAll()
    );

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
