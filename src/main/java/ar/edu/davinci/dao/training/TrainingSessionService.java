package ar.edu.davinci.dao.training;

import ar.edu.davinci.domain.model.training.TrainingSession;
import ar.edu.davinci.dao.FitmeService;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class TrainingSessionService extends FitmeService<TrainingSession, TrainingSession> {

    @Inject
    public TrainingSessionService(SessionFactory sessionFactory) {
        super(TrainingSession.class, sessionFactory);
    }


}
