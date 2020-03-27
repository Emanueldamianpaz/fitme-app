package ar.edu.davinci.controller;

import ar.edu.davinci.infraestructure.Router;
import ar.edu.davinci.controller.exercise.ExerciseRouter;
import ar.edu.davinci.controller.nutrition.NutritionRouter;
import ar.edu.davinci.controller.routine.RoutineRouter;
import ar.edu.davinci.controller.routineTemplate.RoutineTemplateRouter;
import ar.edu.davinci.controller.user.ExerciseSessionRouter;
import ar.edu.davinci.controller.user.UserEntityRouter;
import ar.edu.davinci.controller.user.UserInfoRouter;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

public class RouterModule extends AbstractModule {

    @Override
    protected void configure() {

        Multibinder<Router> routerBinder = Multibinder.newSetBinder(binder(), Router.class);

        routerBinder.addBinding().to(ExerciseRouter.class);
        routerBinder.addBinding().to(NutritionRouter.class);
        routerBinder.addBinding().to(RoutineRouter.class);
        routerBinder.addBinding().to(RoutineTemplateRouter.class);
        routerBinder.addBinding().to(UserEntityRouter.class);
        routerBinder.addBinding().to(UserInfoRouter.class);
        routerBinder.addBinding().to(ExerciseSessionRouter.class);
    }
}
