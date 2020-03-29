package ar.edu.davinci.dao.user.detail;

import ar.edu.davinci.domain.model.user.detail.UserRoutine;
import ar.edu.davinci.dao.FitmeService;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class UserRoutineService extends FitmeService<UserRoutine, UserRoutine> {

    @Inject
    public UserRoutineService(SessionFactory sessionFactory) {
        super(UserRoutine.class, sessionFactory);
    }

}
