package ar.edu.davinci.service;

import ar.edu.davinci.service.exercise.ExerciseService;
import ar.edu.davinci.service.goal.GoalService;
import ar.edu.davinci.service.nutrition.NutritionService;
import ar.edu.davinci.service.routine.RoutineService;
import ar.edu.davinci.service.routineTemplate.RoutineTemplateService;
import ar.edu.davinci.service.scoring.ScoringService;
import ar.edu.davinci.service.user.UserEntityService;
import ar.edu.davinci.service.user.UserInfoService;
import ar.edu.davinci.service.user.UserRoutineService;
import com.google.inject.AbstractModule;

public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ExerciseService.class);
        bind(GoalService.class);
        bind(NutritionService.class);
        bind(RoutineService.class);
        bind(RoutineTemplateService.class);
        bind(ScoringService.class);
        bind(ScoringService.class);
        bind(UserEntityService.class);
        bind(UserInfoService.class);
        bind(UserRoutineService.class);
    }
}
