package ar.edu.davinci.service.user;

import ar.edu.davinci.domain.model.User;
import ar.edu.davinci.service.FitmeService;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.util.Optional;

public class UserEntityService extends FitmeService<User, User> {

    @Inject
    public UserEntityService(SessionFactory sessionFactory) {
        super(User.class, sessionFactory);
    }

    @Override
    public User update(User newInstance) {
        User oldInstance = currentSession().get(clazz, newInstance.getEmail());
        return (User) currentSession().merge(newInstance);
    }

    public User upsert(User user) {
        if (Optional.ofNullable(currentSession().find(User.class, user.getId())).isPresent()) {
            return update(user);
        } else {
            return create(user);
        }
    }
}
