package ar.edu.davinci.service.goal;

import ar.edu.davinci.domain.model.Goal;
import ar.edu.davinci.service.FitmeService;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class GoalService extends FitmeService<Goal, Goal> {

    @Inject
    public GoalService(SessionFactory sessionFactory) {
        super(Goal.class, sessionFactory);
    }

}
