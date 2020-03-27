package ar.edu.davinci.dao.user;

import ar.edu.davinci.domain.model.training.TrainingSession;
import ar.edu.davinci.dao.FitmeService;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class ExerciseSessionService extends FitmeService<TrainingSession, TrainingSession> {

    @Inject
    public ExerciseSessionService(SessionFactory sessionFactory) {
        super(TrainingSession.class, sessionFactory);
    }

}
