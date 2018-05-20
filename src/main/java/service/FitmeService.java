package service;

import domain.FitmeDomain;
import exception.ResourceNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public abstract class FitmeService<ENTITY extends FitmeDomain, PARENT_ENTITY extends FitmeDomain> {

    protected Class<ENTITY> clazz;
    protected SessionFactory sessionFactory;

    public FitmeService(Class<ENTITY> clazz, SessionFactory sessionFactory) {
        this.clazz = clazz;
        this.sessionFactory = sessionFactory;
    }

    public ENTITY get(Long id) {
        ENTITY result = currentSession().get(clazz, id);

        return Optional
                .ofNullable(result)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("%s with id %d%n", clazz.getSimpleName(), id)));
    }

    public ENTITY update(ENTITY newInstance) {
        return (ENTITY) currentSession().merge(newInstance);
    }


    public ENTITY create(ENTITY object) {
        currentSession().save(object);
        return object;
    }

    public ENTITY create(ENTITY object, PARENT_ENTITY parent) {
        currentSession().save(object);
        return object;
    }


    public void delete(Long id) {
        ENTITY object = Optional
                .ofNullable(currentSession().get(clazz, id))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("%s with id %d%n", clazz.getSimpleName(), id)));

        currentSession().delete(object);
    }


    public List<ENTITY> findAll() {
        CriteriaBuilder builder = currentSession().getCriteriaBuilder();

        CriteriaQuery<ENTITY> criteria = builder.createQuery(clazz);
        Root<ENTITY> root = criteria.from(clazz);
        criteria.select(root);

        return currentSession().createQuery(criteria).getResultList();
    }

    protected Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

}
