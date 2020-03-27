package ar.edu.davinci.dao;

import ar.edu.davinci.dao.exercise.ExerciseService;
import ar.edu.davinci.dao.goal.GoalService;
import ar.edu.davinci.dao.nutrition.NutritionService;
import ar.edu.davinci.dao.routine.RoutineService;
import ar.edu.davinci.dao.routineTemplate.RoutineTemplateService;
import ar.edu.davinci.dao.scoring.ScoringService;
import ar.edu.davinci.dao.user.UserEntityService;
import ar.edu.davinci.dao.user.UserInfoService;
import ar.edu.davinci.dao.user.UserRoutineService;
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
