package routers;


import com.google.gson.Gson;
import infraestructure.Router;
import infraestructure.security.UserSession;
import lombok.Getter;
import org.hibernate.SessionFactory;
import spark.Request;


public abstract class FitmeRouter extends Router {

    protected Gson objectMapper;

    @Getter
    public SessionFactory sessionFactory;

    public FitmeRouter(Gson objectMapper, SessionFactory sessionFactory) {
        this.objectMapper = objectMapper;
        this.sessionFactory = sessionFactory;
    }

    protected UserSession getCurrentUserSession(Request request) {
        return request.attribute("current-session");
    }
}
