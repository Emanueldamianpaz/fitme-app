package ar.edu.davinci.routers.routine;

import ar.edu.davinci.domain.model.Routine;
import ar.edu.davinci.dto.routine.RoutineRequestDTO;
import ar.edu.davinci.routers.EnumResponse;
import com.github.racc.tscg.TypesafeConfig;
import com.google.gson.Gson;
import ar.edu.davinci.dto.ResponseDTO;
import org.hibernate.SessionFactory;
import ar.edu.davinci.routers.FitmeRouter;
import ar.edu.davinci.service.routine.RoutineService;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.RouteGroup;
import ar.edu.davinci.utils.JsonTransformer;

import javax.inject.Inject;

import static spark.Spark.*;


public class RoutineRouter extends FitmeRouter {

    private String apiPath;
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

    @Override
    public String path() {
        return apiPath + "/routine";
    }

    @Override
    public RouteGroup routes() {
        return () -> {
            get("", getRoutines, jsonTransformer);
            post("", createRoutine, jsonTransformer);
            patch("", updateRoutine, jsonTransformer);
            delete("", deleteRoutine, jsonTransformer);
        };
    }


    private final Route getRoutines = doInTransaction(false, (Request request, Response response) ->
            routineService.findAll()
    );

    private final Route createRoutine = doInTransaction(false, (Request request, Response response) ->
            {
                RoutineRequestDTO nutritionRequest = (RoutineRequestDTO) jsonTransformer.asJson(request.body(), RoutineRequestDTO.class);
                return routineService.create(new Routine(nutritionRequest));
            }
    );

    private final Route updateRoutine = doInTransaction(false, (Request request, Response response) ->
            {
                RoutineRequestDTO nutritionRequest = (RoutineRequestDTO) jsonTransformer.asJson(request.body(), RoutineRequestDTO.class);
                return routineService.update(new Routine(Long.parseLong(request.params("id")), nutritionRequest));
            }
    );

    private final Route deleteRoutine = doInTransaction(false, (Request request, Response response) ->
            {
                routineService.delete(Long.parseLong(request.params("id")));
                return new ResponseDTO(EnumResponse.DeleteOk.name(), "Rutina eliminada");
            }
    );


}
