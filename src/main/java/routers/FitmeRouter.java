package routers;


import com.google.gson.Gson;
import exception.FitmeException;
import exception.InternalServerErrorException;
import infraestructure.Router;
import infraestructure.security.UserSession;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.context.internal.ManagedSessionContext;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.persistence.EntityTransaction;
import java.util.Optional;
import java.util.function.BiFunction;


public abstract class FitmeRouter extends Router {

    protected Gson objectMapper;

    @Getter
    public SessionFactory sessionFactory;

    public FitmeRouter(Gson objectMapper, SessionFactory sessionFactory) {
        this.objectMapper = objectMapper;
        this.sessionFactory = sessionFactory;
    }

    protected UserSession getCurrentUserSession(Request request) {
        return request.attribute("current-session");
    }

    protected <R> Route doInTransaction(Boolean inTransaction, BiFunction<Request, Response, R> route){

        return (Request request, Response response) ->{

            try (Session session = sessionFactory.openSession()) {

                ManagedSessionContext.bind(session);

                Optional<Transaction> transaction = (inTransaction) ? Optional.of(session.beginTransaction()) : Optional.empty();

                try {

                    R r = route.apply(request, response);

                    transaction.ifPresent(EntityTransaction::commit);

                    return r;

                } catch (FitmeException e) {

                    transaction.ifPresent(EntityTransaction::rollback);

                    e.printStackTrace();

                    throw e;

                } catch (Exception e) {

                    transaction.ifPresent(EntityTransaction::rollback);

                    e.printStackTrace();

                    throw new InternalServerErrorException(e);
                }
            } finally {
                ManagedSessionContext.unbind(sessionFactory);
            }
        };
    }

}
