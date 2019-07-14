package ar.edu.davinci.routers;

import ar.edu.davinci.routers.goal.GoalRouter;
import ar.edu.davinci.routers.routineTemplate.RoutineTemplateRouter;
import ar.edu.davinci.routers.scoring.ScoringRouter;
import ar.edu.davinci.routers.user.UserEntityRouter;
import ar.edu.davinci.routers.user.ExerciseSessionRouter;
import ar.edu.davinci.routers.user.UserInfoRouter;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import ar.edu.davinci.infraestructure.Router;
import ar.edu.davinci.routers.exercise.ExerciseRouter;
import ar.edu.davinci.routers.nutrition.NutritionRouter;
import ar.edu.davinci.routers.routine.RoutineRouter;

public class RouterModule extends AbstractModule {

    @Override
    protected void configure() {

        Multibinder<Router> routerBinder = Multibinder.newSetBinder(binder(), Router.class);

        routerBinder.addBinding().to(ExerciseRouter.class);
        routerBinder.addBinding().to(GoalRouter.class);
        routerBinder.addBinding().to(NutritionRouter.class);
        routerBinder.addBinding().to(RoutineRouter.class);
        routerBinder.addBinding().to(RoutineTemplateRouter.class);
        routerBinder.addBinding().to(ScoringRouter.class);
        routerBinder.addBinding().to(UserEntityRouter.class);
        routerBinder.addBinding().to(UserInfoRouter.class);
        routerBinder.addBinding().to(ExerciseSessionRouter.class);
    }
}
