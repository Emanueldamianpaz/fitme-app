package ar.edu.davinci.dao.routine.detail;

import ar.edu.davinci.domain.model.routine.detail.WorkoutExercise;
import ar.edu.davinci.dao.FitmeService;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class WorkoutExerciseService extends FitmeService<WorkoutExercise, WorkoutExercise> {

    @Inject
    public WorkoutExerciseService(SessionFactory sessionFactory) {
        super(WorkoutExercise.class, sessionFactory);
    }

}
