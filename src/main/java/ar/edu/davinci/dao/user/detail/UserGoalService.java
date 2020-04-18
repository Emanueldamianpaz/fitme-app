package ar.edu.davinci.dao.user.detail;

import ar.edu.davinci.domain.model.user.detail.UserGoal;
import ar.edu.davinci.dao.FitmeService;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class UserGoalService extends FitmeService<UserGoal, UserGoal> {

    @Inject
    public UserGoalService(SessionFactory sessionFactory) {
        super(UserGoal.class, sessionFactory);
    }

}
