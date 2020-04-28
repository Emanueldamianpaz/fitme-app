package ar.edu.davinci.dao.user;

import ar.edu.davinci.dao.FitmeService;
import ar.edu.davinci.domain.dto.fitme.trainning.TrainningTotalStadistDTO;
import ar.edu.davinci.domain.model.training.detail.ExerciseRunningSession;
import ar.edu.davinci.domain.model.training.detail.NutritionSession;
import ar.edu.davinci.domain.model.user.UserEntity;
import ar.edu.davinci.domain.model.user.detail.UserInfo;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserEntityService extends FitmeService<UserEntity, UserEntity> {

    @Inject
    public UserEntityService(SessionFactory sessionFactory) {
        super(UserEntity.class, sessionFactory);
    }

    @Override
    public UserEntity update(UserEntity newInstance) {
        UserEntity oldInstance = currentSession().get(clazz, newInstance.getEmail());
        return (UserEntity) currentSession().merge(newInstance);
    }

    public UserEntity upsert(UserEntity userEntity) {
        if (Optional.ofNullable(currentSession().find(UserEntity.class, userEntity.getId())).isPresent()) {
            return update(userEntity);
        } else {
            return create(userEntity);
        }
    }

    public List<TrainningTotalStadistDTO> getTotalProgressTraining(String idUser) {
        UserInfo userInfo = get(idUser).getUserInfo();

        return userInfo.getTrainingSession().stream().map(trainingSession -> {
            double totalCalories = 0;
            double totalMeters = 0;

            for (ExerciseRunningSession r : trainingSession.getRunningSessions())
                totalMeters += r.getRunningSession().getDistanceCovered();

            for (NutritionSession n : trainingSession.getNutritionSessions())
                totalCalories += n.getCalories();

            return new TrainningTotalStadistDTO(trainingSession.getDate(), totalCalories, totalMeters);
        }).collect(Collectors.toList());
    }
}
