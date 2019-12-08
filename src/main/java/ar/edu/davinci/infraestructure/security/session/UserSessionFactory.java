package ar.edu.davinci.infraestructure.security.session;

import ar.edu.davinci.domain.model.User;
import ar.edu.davinci.domain.model.UserInfo;
import ar.edu.davinci.infraestructure.security.roles.FitmeRoles;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.cache.LoadingCache;
import ar.edu.davinci.infraestructure.security.util.FitmeUser;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static ar.edu.davinci.infraestructure.security.roles.FitmeRoles.READONLY;

@Slf4j
@Singleton
public class UserSessionFactory {

    private LoadingCache<String, UserSession> usersCache;
    private SessionFactory sessionFactory;

    @Inject
    public UserSessionFactory(
            LoadingCache<String, UserSession> usersCache,
            SessionFactory sessionFactory
    ) {
        this.usersCache = usersCache;
        this.sessionFactory = sessionFactory;
    }

    public UserSession createUserSession(DecodedJWT jwt) {
        try {
            return usersCache.get(jwt.getSubject(),
                    () -> {

                        UserSession userSession = new UserSession(jwt);
                        persistUser(userSession);

                        return userSession;
                    });
        } catch (ExecutionException e) {
            log.error("Fail to retrieve a user session " + jwt.getId(), e);

            throw new RuntimeException("Fail to retrieve a user session " + jwt.getId());
        }
    }

    private FitmeUser persistUser(UserSession userSession) {

        FitmeUser user = userSession.getUser();

        try (Session session = sessionFactory.openSession()) {

            Transaction transaction = session.beginTransaction();

            if (Optional.ofNullable(session.find(UserInfo.class, user.getId())).isPresent()) {
                session.update(new UserInfo(user.getId()));
            } else {
                session.save(new UserInfo(user.getId()));
            }


            FitmeRoles role = userSession.getUncheckedRole().orElse(READONLY); // In case that auth0 defaultRole failure
            User u = new User(user, session.get(UserInfo.class, user.getId()), role);

            if (Optional.ofNullable(session.find(User.class, user.getId())).isPresent()) {
                session.update(u);
            } else {
                session.save(u);
            }

            transaction.commit();

        } catch (Exception e) {
            log.error("Fail to persist a user " + user.getId(), e);
        }

        return user;
    }


}
