package ar.edu.davinci.dao.nutrition;

import ar.edu.davinci.domain.model.routine.detail.MealNutrition;
import ar.edu.davinci.dao.FitmeService;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class NutritionService extends FitmeService<MealNutrition, MealNutrition> {

    @Inject
    public NutritionService(SessionFactory sessionFactory) {
        super(MealNutrition.class, sessionFactory);
    }

}
