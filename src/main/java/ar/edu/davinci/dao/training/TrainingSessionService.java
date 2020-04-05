package ar.edu.davinci.dao.training;

import ar.edu.davinci.dao.FitmeService;
import ar.edu.davinci.dao.user.detail.UserInfoService;
import ar.edu.davinci.domain.model.training.TrainingSession;
import ar.edu.davinci.domain.model.training.detail.ExerciseRunningSession;
import ar.edu.davinci.domain.model.training.detail.NutritionSession;
import ar.edu.davinci.domain.model.user.detail.UserInfo;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TrainingSessionService extends FitmeService<TrainingSession, TrainingSession> {

    private UserInfoService userInfoService;

    @Inject
    public TrainingSessionService(SessionFactory sessionFactory,
                                  UserInfoService userInfoService) {
        super(TrainingSession.class, sessionFactory);
        this.userInfoService = userInfoService;
    }

    public UserInfo addExerciseRunningSession(UserInfo userInfo, ExerciseRunningSession request) {

        ExerciseRunningSession ersNew = new ExerciseRunningSession(request);

        try {
            TrainingSession trainingSessionFromToday = findByDate(new Date());
            trainingSessionFromToday.addExerciseRunningSession(ersNew);
            userInfo.addExerciseSession(create(trainingSessionFromToday));
        } catch (NoResultException e) {
            TrainingSession trainingSessionNew = new TrainingSession();
            trainingSessionNew.addExerciseRunningSession(ersNew);
            userInfo.addExerciseSession(create(trainingSessionNew));
        }

        return userInfoService.update(userInfo);

    }

    public UserInfo addNutritionSession(UserInfo userInfo, NutritionSession request) {
        NutritionSession nsNew = new NutritionSession(request);
        try {
            TrainingSession trainingSessionFromToday = findByDate(new Date());
            trainingSessionFromToday.addNutritionSession(nsNew);
            userInfo.addExerciseSession(create(trainingSessionFromToday));
        } catch (NoResultException e) {
            TrainingSession trainingSessionNew = new TrainingSession();
            trainingSessionNew.addNutritionSession(nsNew);
            userInfo.addExerciseSession(create(trainingSessionNew));
        }

        return userInfoService.update(userInfo);

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
