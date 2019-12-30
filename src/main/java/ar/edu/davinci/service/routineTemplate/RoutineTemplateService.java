package ar.edu.davinci.service.routineTemplate;

import ar.edu.davinci.domain.model.Nutrition;
import ar.edu.davinci.domain.model.RoutineTemplate;
import ar.edu.davinci.domain.model.UserInfo;
import ar.edu.davinci.exception.FitmeException;
import ar.edu.davinci.service.FitmeService;
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

        Double gatAbsolute = Math.abs(user.getGoal().getGoalFat() - user.getCurrentFat());

        this.findAll().stream().map(routine -> {

            Stream<Nutrition> nutritionStream = routine.getNutritions().stream();
            Nutrition optimizedNutrition;
            switch (user.getGoal().getType()) {
                case GAIN_WEIGHT:

                    // TODO Implementar como ganar peso
                    optimizedNutrition = nutritionStream
                            .min(Comparator.comparingDouble(i -> Math.abs(i.getCalories() - gatAbsolute)))
                            .orElseThrow(() -> new FitmeException("No value present"));


                    break;

                case LOSS_WEIGHT:
                    optimizedNutrition = nutritionStream
                            .min(Comparator.comparingDouble(i -> Math.abs(i.getCalories() - gatAbsolute)))
                            .orElseThrow(() -> new FitmeException("No value present"));


            }

            return routine;
        });

        return new RoutineTemplate();
    }
}
