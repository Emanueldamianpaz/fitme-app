package ar.edu.davinci.service.exercise;

import ar.edu.davinci.domain.model.Routine;
import org.hibernate.SessionFactory;
import ar.edu.davinci.service.FitmeService;

import javax.inject.Inject;

public class ExerciseService extends FitmeService<Routine, Routine> {

    @Inject
    public ExerciseService(SessionFactory sessionFactory) {
        super(Routine.class, sessionFactory);
    }

}
