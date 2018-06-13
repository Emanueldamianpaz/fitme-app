package ar.edu.davinci.routers.routineTemplate;

import ar.edu.davinci.domain.model.RoutineTemplate;
import ar.edu.davinci.dto.ResponseDTO;
import ar.edu.davinci.dto.routineTemplate.RoutineTemplateRequestDTO;
import ar.edu.davinci.routers.EnumResponse;
import ar.edu.davinci.routers.FitmeRouter;
import ar.edu.davinci.service.routineTemplate.RoutineTemplateService;
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


public class RoutineTemplateRouter extends FitmeRouter {

    private String apiPath;
    private JsonTransformer jsonTransformer;
    private RoutineTemplateService routineTemplateService;

    @Inject
    public RoutineTemplateRouter(Gson objectMapper,
                                 RoutineTemplateService routineTemplateService,
                                 SessionFactory sessionFactory,
                                 JsonTransformer jsonTransformer,
                                 @TypesafeConfig("app.api") String apiPath) {
        super(objectMapper, sessionFactory);
        this.apiPath = apiPath;
        this.routineTemplateService = routineTemplateService;
        this.jsonTransformer = jsonTransformer;
    }

    @Override
    public String path() {
        return apiPath + "/routine_template";
    }

    @Override
    public RouteGroup routes() {
        return () -> {
            get("", getRoutineTemplates, jsonTransformer);
            post("", createRoutineTemplate, jsonTransformer);
            patch("/:id", updateRoutineTemplate, jsonTransformer);
            delete("/:id", deleteRoutineTemplate, jsonTransformer);
        };
    }


    private final Route getRoutineTemplates = doInTransaction(false, (Request request, Response response) ->
            routineTemplateService.findAll()
    );

    private final Route createRoutineTemplate = doInTransaction(false, (Request request, Response response) ->
            {
                RoutineTemplateRequestDTO routineTemplateRequest = (RoutineTemplateRequestDTO) jsonTransformer.asJson(request.body(), RoutineTemplateRequestDTO.class);
                return routineTemplateService.create(new RoutineTemplate(routineTemplateRequest));
            }
    );

    private final Route updateRoutineTemplate = doInTransaction(false, (Request request, Response response) ->
            {
                RoutineTemplateRequestDTO routineTemplateRequest = (RoutineTemplateRequestDTO) jsonTransformer.asJson(request.body(), RoutineTemplateRequestDTO.class);
                return routineTemplateService.update(new RoutineTemplate(Long.parseLong(request.params("id")), routineTemplateRequest));
            }
    );

    private final Route deleteRoutineTemplate = doInTransaction(false, (Request request, Response response) ->
            {
                routineTemplateService.delete(Long.parseLong(request.params("id")));
                return new ResponseDTO(EnumResponse.DeleteOk.name(), "Rutina de ejemplo eliminada");
            }
    );


}
