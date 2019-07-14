package ar.edu.davinci.service.user;

import ar.edu.davinci.domain.model.ExerciseSession;
import ar.edu.davinci.service.FitmeService;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class ExerciseSessionService extends FitmeService<ExerciseSession, ExerciseSession> {

    @Inject
    public ExerciseSessionService(SessionFactory sessionFactory) {
        super(ExerciseSession.class, sessionFactory);
    }

}
