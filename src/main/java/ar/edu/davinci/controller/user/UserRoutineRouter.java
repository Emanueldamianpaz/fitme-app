package ar.edu.davinci.controller.user;

import ar.edu.davinci.controller.FitmeRouter;
import ar.edu.davinci.dao.routine.RoutineTemplateService;
import ar.edu.davinci.dao.user.detail.UserRoutineService;
import ar.edu.davinci.infraestructure.utils.JsonTransformer;
import com.github.racc.tscg.TypesafeConfig;
import com.google.gson.Gson;
import org.hibernate.SessionFactory;
import spark.RouteGroup;

import javax.inject.Inject;


public class UserRoutineRouter extends FitmeRouter {

    private String apiPath;
    private JsonTransformer jsonTransformer;
    private UserRoutineService userRoutineService;
    private RoutineTemplateService routineTemplateService;


    @Inject
    public UserRoutineRouter(Gson objectMapper,
                             SessionFactory sessionFactory,
                             JsonTransformer jsonTransformer,
                             UserRoutineService userRoutineService,
                             RoutineTemplateService routineTemplateService,
                             @TypesafeConfig("app.api") String apiPath) {
        super(objectMapper, sessionFactory);
        this.apiPath = apiPath;
        this.userRoutineService = userRoutineService;
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
         /*   get("", getRoutines, jsonTransformer);
            get("/light", getRoutinesLight, jsonTransformer);

            get("/:id", getRoutine, jsonTransformer);
            get("/:id/info", getInfoRoutine, jsonTransformer); // TODO Candidato a eliminarse

            post("", createRoutine, jsonTransformer);

            patch("/:id", updateRoutine, jsonTransformer);
            delete("/:id", deleteRoutine, jsonTransformer);*/
        };
    }
/*

    private final Route getRoutines = doInTransaction(false, (Request request, Response response) ->
            userRoutineService.findAll()
    );

    private final Route getRoutinesLight = doInTransaction(false, (Request request, Response response) -> {
                List<UserRoutine> list = userRoutineService.findAll();
                List<RoutineLightResponseDTO> routineLight = new ArrayList<>();
                for (Routine routine : list) {
                    routineLight.add(new RoutineLightResponseDTO(routine.getId(), routine.getName(), routine.getDescription()));
                }
                return routineLight;
            }
    );
    private final Route getRoutine = doInTransaction(false, (Request request, Response response) ->
            userRoutineService.get(request.params("id"))
    );

    private final Route getInfoRoutine = doInTransaction(false, (Request request, Response response) -> {
                Routine routine = userRoutineService.get(request.params("id"));

                return routine.getRoutineTemplate();

            }
    );

    private final Route createRoutine = doInTransaction(true, (Request request, Response response) ->
            {
                RoutineRequestDTO routineRequest = (RoutineRequestDTO) jsonTransformer.asJson(request.body(), RoutineRequestDTO.class);

                RoutineTemplate routineTemplate = routineTemplateService.get(routineRequest.getRoutineTemplate());
                return userRoutineService.create(new Routine(routineRequest, routineTemplate));
            }
    );

    private final Route updateRoutine = doInTransaction(true, (Request request, Response response) ->
            {
                RoutineRequestDTO routineRequest = (RoutineRequestDTO) jsonTransformer.asJson(request.body(), RoutineRequestDTO.class);
                RoutineTemplate routineTemplate = routineTemplateService.get(routineRequest.getRoutineTemplate());

                return userRoutineService.update(new Routine(Long.parseLong(request.params("id")), routineRequest, routineTemplate));
            }
    );

    private final Route deleteRoutine = doInTransaction(true, (Request request, Response response) ->
            {
                userRoutineService.delete(request.params("id"));
                return new ResponseBody(EnumResponse.DELETED.name(), "Rutina eliminada");
            }
    );

*/
}
