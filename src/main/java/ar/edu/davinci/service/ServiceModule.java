package ar.edu.davinci.service;

import ar.edu.davinci.service.routineTemplate.RoutineTemplateService;
import com.google.inject.AbstractModule;
import ar.edu.davinci.service.exercise.ExerciseService;
import ar.edu.davinci.service.nutrition.NutritionService;
import ar.edu.davinci.service.routine.RoutineService;

public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {

        bind(RoutineService.class);
        bind(ExerciseService.class);
        bind(NutritionService.class);
        bind(RoutineTemplateService.class);

    }
}
