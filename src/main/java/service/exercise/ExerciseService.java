package service.exercise;

import domain.model.Routine;
import org.hibernate.SessionFactory;
import service.FitmeService;

import javax.inject.Inject;

public class ExerciseService extends FitmeService<Routine, Routine> {

    @Inject
    public ExerciseService(SessionFactory sessionFactory) {
        super(Routine.class, sessionFactory);
    }

}
