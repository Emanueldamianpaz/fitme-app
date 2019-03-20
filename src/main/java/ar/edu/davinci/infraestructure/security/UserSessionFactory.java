package ar.edu.davinci.infraestructure.security;

import com.auth0.jwt.impl.JWTParser;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Payload;
import com.github.racc.tscg.TypesafeConfig;
import com.google.common.cache.LoadingCache;
import ar.edu.davinci.infraestructure.security.util.FitmeUser;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Singleton
public class UserSessionFactory {

    private LoadingCache<String, UserSession> usersCache;
    private SessionFactory sessionFactory;
    private String authorize;

    @Inject
    public UserSessionFactory(
            LoadingCache<String, UserSession> usersCache,
            SessionFactory sessionFactory,
            @TypesafeConfig("auth0.audience2") String authorize
    ) {
        this.usersCache = usersCache;
        this.sessionFactory = sessionFactory;
        this.authorize = authorize;
    }

    public UserSession createUserSession(DecodedJWT jwt) {

        try {
            return usersCache.get(
                    jwt.getSubject(),
                    () -> {

                        UserSession userSession = new UserSession(jwt);
//                        persistUser(userSession.getUser());

                        return userSession;
                    });
        } catch (ExecutionException e) {
            throw new RuntimeException("Fail to retrieve a user session " + jwt.getId());
        }
    }

    private FitmeUser persistUser(FitmeUser user) {
        try (Session session = sessionFactory.openSession()) {

            Transaction transaction = session.beginTransaction();

            session.saveOrUpdate(user);

            transaction.commit();

        } catch (Exception e) {
            throw new RuntimeException("Fail to persist a user " + user.getId());
        }

        return user;
    }

    public UserSession createFakeUserSession(DecodedJWT jwt) {

        try {
            return usersCache.get(jwt.getSubject(), () -> {

                UserSession userSession = new UserSession(jwt);

                //  persistUser(userSession.getUser());

                return userSession;
            });
        } catch (ExecutionException e) {
            throw new RuntimeException("Fail to retrieve a user session " + jwt.getId());
        }
    }
}