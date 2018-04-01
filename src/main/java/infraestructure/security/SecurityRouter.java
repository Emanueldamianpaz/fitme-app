package infraestructure.security;

import infraestructure.conf.Enviroment;
import spark.Router;
import spark.Spark;

public class SecurityRouter implements Router {

    private final String appContext = Enviroment.APP_CONTEXT.getProperty();

    @Override
    public void routeServices() {
        Spark.redirect.any("/" + appContext, "/" + appContext + "/");
    }


}
