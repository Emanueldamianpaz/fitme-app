package ar.edu.davinci.service.user;

import ar.edu.davinci.domain.model.UserRoutine;
import ar.edu.davinci.service.FitmeService;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class UserRoutineService extends FitmeService<UserRoutine, UserRoutine> {

    @Inject
    public UserRoutineService(SessionFactory sessionFactory) {
        super(UserRoutine.class, sessionFactory);
    }

}
