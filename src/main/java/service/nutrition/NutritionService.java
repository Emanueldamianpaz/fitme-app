package service.nutrition;

import domain.model.Routine;
import org.hibernate.SessionFactory;
import service.FitmeService;

import javax.inject.Inject;
import java.util.List;

public class NutritionService extends FitmeService<Routine, Routine> {

    @Inject
    public NutritionService(SessionFactory sessionFactory) {
        super(Routine.class, sessionFactory);
    }

}
