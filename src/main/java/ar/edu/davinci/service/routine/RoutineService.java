package ar.edu.davinci.service.routine;

import ar.edu.davinci.domain.model.Routine;
import org.hibernate.SessionFactory;
import ar.edu.davinci.service.FitmeService;

import javax.inject.Inject;
import java.util.List;

public class RoutineService extends FitmeService<Routine, Routine> {

    @Inject
    public RoutineService(SessionFactory sessionFactory) {
        super(Routine.class, sessionFactory);
    }

    @Override
    public List<Routine> findAll() {
        return super.findAll();
    }

    @Override
    public Routine get(Long id) {
        return super.get(id);
    }
}
