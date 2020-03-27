package ar.edu.davinci.dao.user;

import ar.edu.davinci.domain.model.user.UserEntity;
import ar.edu.davinci.dao.FitmeService;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.util.Optional;

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
}
