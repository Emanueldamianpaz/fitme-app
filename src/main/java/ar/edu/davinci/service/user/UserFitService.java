package ar.edu.davinci.service.user;

import ar.edu.davinci.domain.model.UserFit;
import ar.edu.davinci.service.FitmeService;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class UserFitService extends FitmeService<UserFit, UserFit> {

    @Inject
    public UserFitService(SessionFactory sessionFactory) {
        super(UserFit.class, sessionFactory);
    }

}
