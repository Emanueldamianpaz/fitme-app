package ar.edu.davinci.routers.user;

import ar.edu.davinci.domain.model.UserInfo;
import ar.edu.davinci.dto.ResponseDTO;
import ar.edu.davinci.dto.fitme.user.UserInfoRequestDTO;
import ar.edu.davinci.routers.EnumResponse;
import ar.edu.davinci.routers.FitmeRouter;
import ar.edu.davinci.service.user.UserInfoService;
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
        return apiPath + "/user_info";
    }

    @Override
    public RouteGroup routes() {
        return () -> {
            get("", getListUserInfo, jsonTransformer);

            patch("/:id", updateMyUserInfo, jsonTransformer);
            delete("/:id", deleteUserInfo, jsonTransformer);
        };
    }


    private final Route getListUserInfo = doInTransaction(false, (Request request, Response response) ->
            userInfoService.findAll()
    );

    private final Route updateMyUserInfo = doInTransaction(true, (Request request, Response response) ->
            {
                UserInfoRequestDTO userInfoRequest = (UserInfoRequestDTO) jsonTransformer.asJson(request.body(), UserInfoRequestDTO.class);
                return userInfoService.update(new UserInfo(request.params("id"), userInfoRequest));
            }
    );

    private final Route deleteUserInfo = doInTransaction(true, (Request request, Response response) ->
            {
                userInfoService.delete(request.params("id"));
                return new ResponseDTO(EnumResponse.DeleteOk.name(), "Información del usuario eliminado eliminada");
            }
    );
}