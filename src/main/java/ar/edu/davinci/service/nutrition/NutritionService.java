package ar.edu.davinci.service.nutrition;

import ar.edu.davinci.domain.model.Routine;
import org.hibernate.SessionFactory;
import ar.edu.davinci.service.FitmeService;

import javax.inject.Inject;

public class NutritionService extends FitmeService<Routine, Routine> {

    @Inject
    public NutritionService(SessionFactory sessionFactory) {
        super(Routine.class, sessionFactory);
    }

}
