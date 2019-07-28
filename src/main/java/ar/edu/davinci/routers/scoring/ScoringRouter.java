package ar.edu.davinci.routers.scoring;

import ar.edu.davinci.domain.model.Scoring;
import ar.edu.davinci.domain.model.User;
import ar.edu.davinci.dto.ResponseDTO;
import ar.edu.davinci.dto.fitme.scoring.ScoringRequestDTO;
import ar.edu.davinci.dto.fitme.scoring.TipRequestDTO;
import ar.edu.davinci.routers.EnumResponse;
import ar.edu.davinci.routers.FitmeRouter;
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

import static spark.Spark.*;

public class ScoringRouter extends FitmeRouter {

    private String apiPath;
    private JsonTransformer jsonTransformer;
    private ScoringService scoringService;
    private UserEntityService userEntityService;

    @Inject
    public ScoringRouter(Gson objectMapper,
                         ScoringService scoringService,
                         SessionFactory sessionFactory,
                         UserEntityService userEntityService,
                         JsonTransformer jsonTransformer,
                         @TypesafeConfig("app.api") String apiPath) {
        super(objectMapper, sessionFactory);
        this.apiPath = apiPath;
        this.scoringService = scoringService;
        this.userEntityService = userEntityService;
        this.jsonTransformer = jsonTransformer;
    }

    @Override
    public String path() {
        return apiPath + "/scoring";
    }

    @Override
    public RouteGroup routes() {
        return () -> {
            get("", getScorings, jsonTransformer);
            get("/:id", getScoring, jsonTransformer);
            post("", createScoring, jsonTransformer);
            patch("/:user_id", updateScoring, jsonTransformer);
            delete("/:id", deleteExercise, jsonTransformer);
        };
    }


    private final Route getScorings = doInTransaction(false, (Request request, Response response) ->
            scoringService.findAll()
    );

    private final Route getScoring = doInTransaction(false, (Request request, Response response) ->
            scoringService.get(request.params("id"))
    );

    private final Route createScoring = doInTransaction(true, (Request request, Response response) ->
            {
                ScoringRequestDTO scoringRequest = (ScoringRequestDTO) jsonTransformer.asJson(request.body(), ScoringRequestDTO.class);
                return scoringService.create(new Scoring(scoringRequest));
            }
    );

    private final Route updateScoring = doInTransaction(true, (Request request, Response response) ->
            {
                String userId = request.params("user_id");
                TipRequestDTO tip = (TipRequestDTO) jsonTransformer.asJson(request.body(), TipRequestDTO.class);

                User user = userEntityService.get(userId);
                Scoring scoring = user.getUserRoutine().getScoring();
                scoring.setTip(tip.getTip());

                return scoringService.update(scoring);
            }
    );

    private final Route deleteExercise = doInTransaction(true, (Request request, Response response) ->
            {
                scoringService.delete(Long.parseLong(request.params("id")));
                return new ResponseDTO(EnumResponse.DeleteOk.name(), "Puntuación eliminada");
            }
    );
}