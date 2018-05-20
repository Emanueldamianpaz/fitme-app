package service.routine;

import domain.model.RoutineObject;
import org.hibernate.SessionFactory;
import service.FitmeService;

import javax.inject.Inject;

public class RoutineService extends FitmeService<RoutineObject, RoutineObject> {

    @Inject
    public RoutineService(SessionFactory sessionFactory) {
        super(RoutineObject.class, sessionFactory);
    }
}
