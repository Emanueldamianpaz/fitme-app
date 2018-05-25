package routers;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import infraestructure.Router;
import routers.exercise.ExerciseRouter;
import routers.nutrition.NutritionRouter;
import routers.routine.RoutineRouter;

public class RouterModule extends AbstractModule {

    @Override
    protected void configure() {

        Multibinder<Router> routerBinder = Multibinder.newSetBinder(binder(), Router.class);

        routerBinder.addBinding().to(RoutineRouter.class);
        routerBinder.addBinding().to(ExerciseRouter.class);
        routerBinder.addBinding().to(NutritionRouter.class);


    }
}
