package ar.edu.davinci.controller;


import ar.edu.davinci.infraestructure.Router;
import ar.edu.davinci.infraestructure.exception.FitmeException;
import ar.edu.davinci.infraestructure.exception.runtime.InternalServerErrorException;
import ar.edu.davinci.infraestructure.exception.runtime.UnauthorizedOperationException;
import com.google.gson.Gson;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.context.internal.ManagedSessionContext;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
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

    protected <R> Route doInTransaction(Boolean inTransaction, BiFunction<Request, Response, R> route) {

        return (Request request, Response response) -> {

            try (Session session = sessionFactory.openSession()) {

                ManagedSessionContext.bind(session);

                Optional<Transaction> transaction = (inTransaction) ? Optional.of(session.beginTransaction()) : Optional.empty();

                try {

                    R r = route.apply(request, response);

                    transaction.ifPresent(EntityTransaction::commit);

                    return r;
                } catch (PersistenceException e) {
                    transaction.ifPresent(EntityTransaction::rollback);

                    throw new UnauthorizedOperationException("Resource used by other entity");

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
