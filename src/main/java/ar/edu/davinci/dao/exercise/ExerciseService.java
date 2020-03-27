package ar.edu.davinci.dao.exercise;

import ar.edu.davinci.domain.model.routine.detail.WorkoutExercise;
import ar.edu.davinci.dao.FitmeService;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class ExerciseService extends FitmeService<WorkoutExercise, WorkoutExercise> {

    @Inject
    public ExerciseService(SessionFactory sessionFactory) {
        super(WorkoutExercise.class, sessionFactory);
    }

}
