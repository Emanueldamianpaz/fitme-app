package ar.edu.davinci.dao.goal;

import ar.edu.davinci.domain.model.user.detail.UserGoal;
import ar.edu.davinci.dao.FitmeService;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class GoalService extends FitmeService<UserGoal, UserGoal> {

    @Inject
    public GoalService(SessionFactory sessionFactory) {
        super(UserGoal.class, sessionFactory);
    }

}
