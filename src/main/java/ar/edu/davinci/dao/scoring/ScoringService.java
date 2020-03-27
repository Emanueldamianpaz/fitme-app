package ar.edu.davinci.dao.scoring;

import ar.edu.davinci.domain.model.user.detail.UserExperience;
import ar.edu.davinci.dao.FitmeService;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class ScoringService extends FitmeService<UserExperience, UserExperience> {

    @Inject
    public ScoringService(SessionFactory sessionFactory) {
        super(UserExperience.class, sessionFactory);
    }

}
