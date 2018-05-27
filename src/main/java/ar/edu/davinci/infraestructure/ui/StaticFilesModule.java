package ar.edu.davinci.infraestructure.ui;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import ar.edu.davinci.infraestructure.Router;

public class StaticFilesModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(StaticFilesRouter.class);

        Multibinder<Router> routerBinder = Multibinder.newSetBinder(binder(), Router.class);
        routerBinder.addBinding().to(StaticFilesRouter.class);
    }

}
