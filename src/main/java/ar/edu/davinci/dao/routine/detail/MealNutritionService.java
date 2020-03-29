package ar.edu.davinci.dao.routine.detail;

import ar.edu.davinci.domain.model.routine.detail.MealNutrition;
import ar.edu.davinci.dao.FitmeService;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class MealNutritionService extends FitmeService<MealNutrition, MealNutrition> {

    @Inject
    public MealNutritionService(SessionFactory sessionFactory) {
        super(MealNutrition.class, sessionFactory);
    }

}
