package infraestructure.security;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import spark.Router;
import spark.StaticFilesRouter;

public class SecurityModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<Router> routerBinder = Multibinder.newSetBinder(binder(), Router.class);
        routerBinder.addBinding().to(SecurityRouter.class);
        routerBinder.addBinding().to(StaticFilesRouter.class);

    }

}
