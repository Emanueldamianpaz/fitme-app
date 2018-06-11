package ar.edu.davinci.service.routineTemplate;

import ar.edu.davinci.domain.model.RoutineTemplate;
import ar.edu.davinci.service.FitmeService;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.util.List;

public class RoutineTemplateService extends FitmeService<RoutineTemplate, RoutineTemplate> {

    @Inject
    public RoutineTemplateService(SessionFactory sessionFactory) {
        super(RoutineTemplate.class, sessionFactory);
    }

    @Override
    public List<RoutineTemplate> findAll() {
        return super.findAll();
    }

    @Override
    public RoutineTemplate get(Long id) {
        return super.get(id);
    }
}
