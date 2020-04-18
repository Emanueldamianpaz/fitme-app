package ar.edu.davinci.controller.user;

import ar.edu.davinci.controller.FitmeRouter;
import ar.edu.davinci.dao.user.detail.UserInfoService;
import ar.edu.davinci.domain.dto.fitme.user.UserInfoRequestDTO;
import ar.edu.davinci.domain.model.user.detail.UserInfo;
import ar.edu.davinci.infraestructure.utils.JsonTransformer;
import com.github.racc.tscg.TypesafeConfig;
import com.google.gson.Gson;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.RouteGroup;

import javax.inject.Inject;

import static spark.Spark.get;
import static spark.Spark.patch;

public class UserInfoRouter extends FitmeRouter {

    private String apiPath;
    private JsonTransformer jsonTransformer;
    private UserInfoService userInfoService;

    @Inject
    public UserInfoRouter(Gson objectMapper,
                          UserInfoService userInfoService,
                          SessionFactory sessionFactory,
                          JsonTransformer jsonTransformer,
                          @TypesafeConfig("app.api") String apiPath) {
        super(objectMapper, sessionFactory);
        this.apiPath = apiPath;
        this.userInfoService = userInfoService;
        this.jsonTransformer = jsonTransformer;
    }

    @Override
    public String path() {
        return apiPath + "/user-info";
    }

    @Override
    public RouteGroup routes() {
        return () -> {
            get("", getListUserInfo, jsonTransformer);
            patch("/:id", updateMyUserInfo, jsonTransformer);
        };
    }

    private final Route getListUserInfo = doInTransaction(false, (Request request, Response response) ->
            userInfoService.findAll()
    );

    private final Route updateMyUserInfo = doInTransaction(true, (Request request, Response response) ->
            {
                UserInfoRequestDTO userInfoRequest = (UserInfoRequestDTO) jsonTransformer.asJson(request.body(), UserInfoRequestDTO.class);
                return userInfoService.update(new UserInfo(Long.parseLong(request.params("id")), userInfoRequest));
            }
    );

}
