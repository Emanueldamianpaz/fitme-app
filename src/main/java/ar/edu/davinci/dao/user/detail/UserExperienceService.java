package ar.edu.davinci.dao.user.detail;

import ar.edu.davinci.domain.model.user.detail.UserExperience;
import ar.edu.davinci.dao.FitmeService;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class UserExperienceService extends FitmeService<UserExperience, UserExperience> {

    @Inject
    public UserExperienceService(SessionFactory sessionFactory) {
        super(UserExperience.class, sessionFactory);
    }

}
