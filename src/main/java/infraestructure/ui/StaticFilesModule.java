package infraestructure.ui;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import infraestructure.Router;

public class StaticFilesModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(StaticFilesRouter.class);

        Multibinder<Router> routerBinder = Multibinder.newSetBinder(binder(), Router.class);
        routerBinder.addBinding().to(StaticFilesRouter.class);
    }

}
