package ar.edu.davinci.service.exercise;

import ar.edu.davinci.domain.model.Exercise;
import org.hibernate.SessionFactory;
import ar.edu.davinci.service.FitmeService;

import javax.inject.Inject;

public class ExerciseService extends FitmeService<Exercise, Exercise> {

    @Inject
    public ExerciseService(SessionFactory sessionFactory) {
        super(Exercise.class, sessionFactory);
    }

}