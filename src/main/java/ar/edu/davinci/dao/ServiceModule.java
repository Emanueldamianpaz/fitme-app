package ar.edu.davinci.dao;

import ar.edu.davinci.dao.routine.detail.WorkoutExerciseService;
import ar.edu.davinci.dao.routine.detail.MealNutritionService;
import ar.edu.davinci.dao.routine.RoutineTemplateService;
import ar.edu.davinci.dao.user.detail.UserExperienceService;
import ar.edu.davinci.dao.user.UserEntityService;
import ar.edu.davinci.dao.user.detail.UserInfoService;
import ar.edu.davinci.dao.user.detail.UserRoutineService;
import ar.edu.davinci.dao.user.detail.GoalService;
import ar.edu.davinci.domain.model.user.detail.UserRoutine;
import com.google.inject.AbstractModule;

public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(WorkoutExerciseService.class);
        bind(GoalService.class);
        bind(MealNutritionService.class);
        bind(RoutineTemplateService.class);
        bind(UserExperienceService.class);
        bind(UserExperienceService.class);
        bind(UserEntityService.class);
        bind(UserInfoService.class);
        bind(UserRoutineService.class);
        bind(UserRoutine.class);

    }
}
