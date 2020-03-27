package ar.edu.davinci.dao.routineTemplate;

import ar.edu.davinci.domain.model.routine.RoutineTemplate;
import ar.edu.davinci.domain.model.routine.detail.MealNutrition;
import ar.edu.davinci.domain.model.user.detail.UserInfo;
import ar.edu.davinci.infraestructure.exception.FitmeException;
import ar.edu.davinci.dao.FitmeService;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class RoutineTemplateService extends FitmeService<RoutineTemplate, RoutineTemplate> {


    @Inject
    public RoutineTemplateService(SessionFactory sessionFactory) {
        super(RoutineTemplate.class, sessionFactory);
    }

    @Override
    public List<RoutineTemplate> findAll() {
        return super.findAll();
    }

    public RoutineTemplate getOptimizedRoutineTemplate(UserInfo user) {

        Double gatAbsolute = Math.abs(user.getUserGoal().getGoalFat() - user.getCurrentFat());

        this.findAll().stream().map(routine -> {

            Stream<MealNutrition> nutritionStream = routine.getMealNutritions().stream();
            MealNutrition optimizedMealNutrition;
            switch (user.getUserGoal().getType()) {
                case GAIN_WEIGHT:

                    // TODO Implementar como ganar peso
                    optimizedMealNutrition = nutritionStream
                            .min(Comparator.comparingDouble(i -> Math.abs(i.getCalories() - gatAbsolute)))
                            .orElseThrow(() -> new FitmeException("No value present"));


                    break;

                case LOSS_WEIGHT:
                    optimizedMealNutrition = nutritionStream
                            .min(Comparator.comparingDouble(i -> Math.abs(i.getCalories() - gatAbsolute)))
                            .orElseThrow(() -> new FitmeException("No value present"));


            }

            return routine;
        });

        return new RoutineTemplate();
    }
}
