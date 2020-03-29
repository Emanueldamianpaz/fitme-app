package ar.edu.davinci.controller;

import ar.edu.davinci.controller.routine.RoutineTemplateRouter;

import ar.edu.davinci.controller.routine.detail.MealNutritionRouter;
import ar.edu.davinci.controller.training.TrainingSessionRouter;
import ar.edu.davinci.controller.routine.detail.WorkoutExerciseRouter;
import ar.edu.davinci.controller.user.UserEntityRouter;
import ar.edu.davinci.controller.user.UserInfoRouter;
import ar.edu.davinci.controller.user.UserRoutineRouter;
import ar.edu.davinci.infraestructure.Router;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

public class RouterModule extends AbstractModule {

    @Override
    protected void configure() {

        Multibinder<Router> routerBinder = Multibinder.newSetBinder(binder(), Router.class);

        routerBinder.addBinding().to(WorkoutExerciseRouter.class);
        routerBinder.addBinding().to(MealNutritionRouter.class);
        routerBinder.addBinding().to(UserRoutineRouter.class);
        routerBinder.addBinding().to(RoutineTemplateRouter.class);
        routerBinder.addBinding().to(UserEntityRouter.class);
        routerBinder.addBinding().to(UserInfoRouter.class);
        routerBinder.addBinding().to(TrainingSessionRouter.class);
    }
}
