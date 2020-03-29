package ar.edu.davinci.dao.user.detail;

import ar.edu.davinci.domain.model.user.detail.UserInfo;
import ar.edu.davinci.dao.FitmeService;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class UserInfoService extends FitmeService<UserInfo, UserInfo> {

    @Inject
    public UserInfoService(SessionFactory sessionFactory) {
        super(UserInfo.class, sessionFactory);
    }

}
