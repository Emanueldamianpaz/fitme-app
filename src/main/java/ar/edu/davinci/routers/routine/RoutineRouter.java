package ar.edu.davinci.routers.routine;

import ar.edu.davinci.domain.model.Routine;
import ar.edu.davinci.domain.model.RoutineTemplate;
import ar.edu.davinci.dto.fitme.routine.RoutineRequestDTO;
import ar.edu.davinci.routers.EnumResponse;
import ar.edu.davinci.service.routineTemplate.RoutineTemplateService;
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
    private RoutineTemplateService routineTemplateService;

    @Inject
    public RoutineRouter(Gson objectMapper,
                         RoutineService routineService,
                         SessionFactory sessionFactory,
                         JsonTransformer jsonTransformer,
                         RoutineTemplateService routineTemplateService,
                         @TypesafeConfig("app.api") String apiPath) {
        super(objectMapper, sessionFactory);
        this.apiPath = apiPath;
        this.routineService = routineService;
        this.jsonTransformer = jsonTransformer;
        this.routineTemplateService = routineTemplateService;
    }

    @Override
    public String path() {
        return apiPath + "/routine";
    }

    @Override
    public RouteGroup routes() {
        return () -> {
            get("", getRoutines, jsonTransformer);
            get("/:id", getRoutine, jsonTransformer);
            post("", createRoutine, jsonTransformer);
            patch("/:id", updateRoutine, jsonTransformer);
            delete("/:id", deleteRoutine, jsonTransformer);
        };
    }


    private final Route getRoutines = doInTransaction(false, (Request request, Response response) ->
            routineService.findAll()
    );

    private final Route getRoutine = doInTransaction(false, (Request request, Response response) ->
            routineService.get(request.params("id"))
    );

    private final Route createRoutine = doInTransaction(true, (Request request, Response response) ->
            {
                RoutineRequestDTO routineRequest = (RoutineRequestDTO) jsonTransformer.asJson(request.body(), RoutineRequestDTO.class);

                RoutineTemplate routineTemplate = routineTemplateService.get(routineRequest.getRoutineTemplate());
                return routineService.create(new Routine(routineRequest, routineTemplate));
            }
    );

    private final Route updateRoutine = doInTransaction(true, (Request request, Response response) ->
            {
                RoutineRequestDTO routineRequest = (RoutineRequestDTO) jsonTransformer.asJson(request.body(), RoutineRequestDTO.class);
                RoutineTemplate routineTemplate = routineTemplateService.get(routineRequest.getRoutineTemplate());

                return routineService.update(new Routine(Long.parseLong(request.params("id")), routineRequest, routineTemplate));
            }
    );

    private final Route deleteRoutine = doInTransaction(true, (Request request, Response response) ->
            {
                routineService.delete(request.params("id"));
                return new ResponseDTO(EnumResponse.DeleteOk.name(), "Rutina eliminada");
            }
    );


}
