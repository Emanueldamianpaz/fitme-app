package ar.edu.davinci.service.scoring;

import ar.edu.davinci.domain.model.Scoring;
import ar.edu.davinci.service.FitmeService;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class ScoringService extends FitmeService<Scoring, Scoring> {

    @Inject
    public ScoringService(SessionFactory sessionFactory) {
        super(Scoring.class, sessionFactory);
    }

}
