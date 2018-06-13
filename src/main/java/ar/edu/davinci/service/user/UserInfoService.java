package ar.edu.davinci.service.user;

import ar.edu.davinci.domain.model.UserInfo;
import ar.edu.davinci.service.FitmeService;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class UserInfoService extends FitmeService<UserInfo, UserInfo> {

    @Inject
    public UserInfoService(SessionFactory sessionFactory) {
        super(UserInfo.class, sessionFactory);
    }

}
