package ar.edu.davinci.routers.goal;

import ar.edu.davinci.domain.model.Goal;
import ar.edu.davinci.dto.ResponseDTO;
import ar.edu.davinci.dto.fitme.goal.GoalRequestDTO;
import ar.edu.davinci.routers.EnumResponse;
import ar.edu.davinci.routers.FitmeRouter;
import ar.edu.davinci.service.goal.GoalService;
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

public class GoalRouter extends FitmeRouter {

    private String apiPath;
    private JsonTransformer jsonTransformer;
    private GoalService goalService;

    @Inject
    public GoalRouter(Gson objectMapper,
                      GoalService goalService,
                      SessionFactory sessionFactory,
                      JsonTransformer jsonTransformer,
                      @TypesafeConfig("app.api") String apiPath) {
        super(objectMapper, sessionFactory);
        this.apiPath = apiPath;
        this.goalService = goalService;
        this.jsonTransformer = jsonTransformer;
    }

    @Override
    public String path() {
        return apiPath + "/goal";
    }

    @Override
    public RouteGroup routes() {
        return () -> {

            // TODO Candidato para eliminar
            get("", getGoals, jsonTransformer);
            get("/:id", getGoal, jsonTransformer);
            post("", createGoal, jsonTransformer);
            patch("/:id", updateGoal, jsonTransformer);
            delete("/:id", deleteGoal, jsonTransformer);
        };
    }

    private final Route getGoals = doInTransaction(false, (Request request, Response response) ->
            goalService.findAll()
    );

    private final Route getGoal = doInTransaction(false, (Request request, Response response) ->
            goalService.get(request.params("id"))
    );

    private final Route createGoal = doInTransaction(true, (Request request, Response response) ->
            {
                GoalRequestDTO goalRequest = (GoalRequestDTO) jsonTransformer.asJson(request.body(), GoalRequestDTO.class);
                return goalService.create(new Goal(goalRequest));
            }
    );

    private final Route updateGoal = doInTransaction(true, (Request request, Response response) ->
            {
                GoalRequestDTO goalRequest = (GoalRequestDTO) jsonTransformer.asJson(request.body(), GoalRequestDTO.class);
                return goalService.update(new Goal(Long.parseLong(request.params("id")), goalRequest));
            }
    );

    private final Route deleteGoal = doInTransaction(true, (Request request, Response response) ->
            {
                goalService.delete(Long.parseLong(request.params("id")));
                return new ResponseDTO(EnumResponse.DeleteOk.name(), "Objetivo eliminado");
            }
    );
}
