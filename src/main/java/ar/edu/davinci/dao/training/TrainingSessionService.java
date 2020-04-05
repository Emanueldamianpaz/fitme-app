package ar.edu.davinci.dao.training;

import ar.edu.davinci.dao.FitmeService;
import ar.edu.davinci.domain.model.training.TrainingSession;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TrainingSessionService extends FitmeService<TrainingSession, TrainingSession> {

    @Inject
    public TrainingSessionService(SessionFactory sessionFactory) {
        super(TrainingSession.class, sessionFactory);
    }

    public TrainingSession findByDate(Date date) {
        String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(date);

        CriteriaBuilder builder = currentSession().getCriteriaBuilder();
        CriteriaQuery<TrainingSession> query = builder.createQuery(TrainingSession.class);
        Root<TrainingSession> root = query.from(TrainingSession.class);

        Query q = currentSession().createQuery(query.select(root).where(builder.equal(root.get("date"), currentDate)));

        return (TrainingSession) q.getSingleResult();
    }
}
