package routers;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import infraestructure.Router;
import routers.routine.RoutineRouter;

public class RouterModule extends AbstractModule {

    @Override
    protected void configure() {

        Multibinder<Router> routerBinder = Multibinder.newSetBinder(binder(), Router.class);

        routerBinder.addBinding().to(RoutineRouter.class);
    }
}
