package ar.edu.davinci.infraestructure.security.session;

import ar.edu.davinci.dao.user.UserEntityService;
import ar.edu.davinci.dao.user.detail.UserGoalService;
import ar.edu.davinci.dao.user.detail.UserInfoService;
import ar.edu.davinci.domain.FitmeRoles;
import ar.edu.davinci.domain.model.user.UserEntity;
import ar.edu.davinci.domain.model.user.detail.UserGoal;
import ar.edu.davinci.domain.model.user.detail.UserInfo;
import ar.edu.davinci.infraestructure.exception.FitmeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.ExecutionException;

@Slf4j
@Singleton
public class UserSessionFactory {

    private LoadingCache<String, UserSession> usersCache;
    private SessionFactory sessionFactory;
    private UserEntityService userEntityService;
    private UserGoalService userGoalService;
    private UserInfoService userInfoService;

    @Inject
    public UserSessionFactory(
            LoadingCache<String, UserSession> usersCache,
            SessionFactory sessionFactory,
            UserEntityService userEntityService,
            UserGoalService userGoalService,
            UserInfoService userInfoService
    ) {
        this.usersCache = usersCache;
        this.sessionFactory = sessionFactory;
        this.userEntityService = userEntityService;
        this.userGoalService = userGoalService;
        this.userInfoService = userInfoService;
    }

    public UserSession createUserSession(DecodedJWT jwt, FitmeRoles role) {
        try {
            return usersCache.get(jwt.getSubject(),
                    () -> {
                        UserSession userSession = new UserSession(jwt);
                        persistUser(userSession, role);

                        return userSession;
                    });
        } catch (ExecutionException e) {
            log.error("Fail to retrieve a user session " + jwt.getId(), e);
            throw new FitmeException("Fail to retrieve a user session " + jwt.getId());
        }
    }

    private FitmeUser persistUser(UserSession userSession, FitmeRoles role) {
        FitmeUser user = userSession.getUser();

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            UserGoal userGoal = userGoalService.create(new UserGoal(user.getId()));
            UserInfo userInfo = userInfoService.create(new UserInfo(user.getId(), userGoal));

            UserEntity userEntity = new UserEntity(user, role);
            userEntity.setUserInfo(userInfo);

            userEntityService.upsert(userEntity);

            transaction.commit();
        } catch (Exception e) {
            log.error("Fail to persist a user " + user.getId(), e);
        }

        return user;
    }


}
