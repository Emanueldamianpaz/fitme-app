package ar.edu.davinci.service.nutrition;

import ar.edu.davinci.domain.model.Nutrition;
import ar.edu.davinci.domain.model.Routine;
import org.hibernate.SessionFactory;
import ar.edu.davinci.service.FitmeService;

import javax.inject.Inject;

public class NutritionService extends FitmeService<Nutrition, Nutrition> {

    @Inject
    public NutritionService(SessionFactory sessionFactory) {
        super(Nutrition.class, sessionFactory);
    }

}
