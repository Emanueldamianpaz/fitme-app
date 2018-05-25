package service;

import com.google.inject.AbstractModule;
import service.exercise.ExerciseService;
import service.nutrition.NutritionService;
import service.routine.RoutineService;

public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {

        bind(RoutineService.class);
        bind(ExerciseService.class);
        bind(NutritionService.class);

    }
}
