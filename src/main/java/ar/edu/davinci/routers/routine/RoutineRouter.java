package ar.edu.davinci.routers.routine;

import ar.edu.davinci.domain.model.*;
import ar.edu.davinci.domain.types.ScoringType;
import ar.edu.davinci.dto.ResponseDTO;
import ar.edu.davinci.dto.fitme.routine.RoutineLightResponseDTO;
import ar.edu.davinci.dto.fitme.routine.RoutineRequestDTO;
import ar.edu.davinci.routers.EnumResponse;
import ar.edu.davinci.routers.FitmeRouter;
import ar.edu.davinci.service.routine.RoutineService;
import ar.edu.davinci.service.routineTemplate.RoutineTemplateService;
import ar.edu.davinci.service.scoring.ScoringService;
import ar.edu.davinci.service.user.UserEntityService;
import ar.edu.davinci.utils.JsonTransformer;
import com.github.racc.tscg.TypesafeConfig;
import com.google.gson.Gson;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.RouteGroup;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static spark.Spark.*;


public class RoutineRouter extends FitmeRouter {

    private String apiPath;
    private JsonTransformer jsonTransformer;
    private RoutineService routineService;
    private RoutineTemplateService routineTemplateService;
    private UserEntityService userEntityService;
    private ScoringService scoringService;


    @Inject
    public RoutineRouter(Gson objectMapper,
                         RoutineService routineService,
                         SessionFactory sessionFactory,
                         JsonTransformer jsonTransformer,
                         ScoringService scoringService,
                         UserEntityService userEntityService,
                         RoutineTemplateService routineTemplateService,
                         @TypesafeConfig("app.api") String apiPath) {
        super(objectMapper, sessionFactory);
        this.apiPath = apiPath;
        this.routineService = routineService;
        this.jsonTransformer = jsonTransformer;
        this.scoringService = scoringService;
        this.userEntityService = userEntityService;
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
            get("/light", getRoutinesLight, jsonTransformer);

            get("/:id", getRoutine, jsonTransformer);
            get("/:id/info", getInfoRoutine, jsonTransformer); // TODO Candidato a eliminarse

            post("", createRoutine, jsonTransformer);

            patch("/:id", updateRoutine, jsonTransformer);
            delete("/:id", deleteRoutine, jsonTransformer);
        };
    }


    private final Route getRoutines = doInTransaction(false, (Request request, Response response) ->
            routineService.findAll()
    );

    private final Route getRoutinesLight = doInTransaction(false, (Request request, Response response) -> {
                List<Routine> list = routineService.findAll();
                List<RoutineLightResponseDTO> routineLight = new ArrayList<>();
                for (Routine routine : list) {
                    routineLight.add(new RoutineLightResponseDTO(routine.getId(), routine.getName(), routine.getDescription()));
                }
                return routineLight;
            }
    );
    private final Route getRoutine = doInTransaction(false, (Request request, Response response) ->
            routineService.get(request.params("id"))
    );

    private final Route getInfoRoutine = doInTransaction(false, (Request request, Response response) -> {
                Routine routine = routineService.get(request.params("id"));

                return routine.getRoutineTemplate();

            }
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
