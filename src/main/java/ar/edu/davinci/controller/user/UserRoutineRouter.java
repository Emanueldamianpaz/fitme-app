package ar.edu.davinci.controller.user;

import ar.edu.davinci.controller.FitmeRouter;
import ar.edu.davinci.dao.routine.RoutineTemplateService;
import ar.edu.davinci.dao.user.detail.UserRoutineService;
import ar.edu.davinci.infraestructure.security.session.UserSessionFactory;
import ar.edu.davinci.infraestructure.utils.JsonTransformer;
import com.github.racc.tscg.TypesafeConfig;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.RouteGroup;

import javax.inject.Inject;

import static spark.Spark.get;

@Slf4j
public class UserRoutineRouter extends FitmeRouter {

    private JsonTransformer jsonTransformer;
    private UserSessionFactory userSessionFactory;
    private UserRoutineService userRoutineService;
    private RoutineTemplateService routineTemplateService;
    private String apiPath;

    @Inject
    public UserRoutineRouter(Gson objectMapper,
                             SessionFactory sessionFactory,
                             UserSessionFactory userSessionFactory,
                             @TypesafeConfig("app.api") String apiPath,
                             UserRoutineService userRoutineService,
                             RoutineTemplateService routineTemplateService,
                             JsonTransformer jsonTransformer) {
        super(objectMapper, sessionFactory);
        this.userRoutineService = userRoutineService;
        this.apiPath = apiPath;
        this.userSessionFactory = userSessionFactory;
        this.routineTemplateService = routineTemplateService;
        this.jsonTransformer = jsonTransformer;
    }

    @Override
    public String path() {
        return apiPath + "/user-routine";
    }
    @Override
    public RouteGroup routes() {
        return () -> {
            get("", getUserRoutines, jsonTransformer);
          //  get("/callback", callbackSession, jsonTransformer); // Only for Admin/Coach/Web

        };
    }

    private final Route getUserRoutines = doInTransaction(false, (Request request, Response response) -> {
                Long routineTemplateId = Long.parseLong(request.queryParams("routine_template_id"));

                return userRoutineService.getUserExperiencesFromRoutineTemplate(routineTemplateId);
            }
    );


}
