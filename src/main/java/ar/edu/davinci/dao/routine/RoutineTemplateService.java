package ar.edu.davinci.dao.routine;

import ar.edu.davinci.dao.FitmeService;
import ar.edu.davinci.domain.model.routine.RoutineTemplate;
import ar.edu.davinci.domain.model.user.detail.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class RoutineTemplateService extends FitmeService<RoutineTemplate, RoutineTemplate> {


    @Inject
    public RoutineTemplateService(SessionFactory sessionFactory) {
        super(RoutineTemplate.class, sessionFactory);
    }

    @Override
    public List<RoutineTemplate> findAll() {
        return super.findAll();
    }

    public RoutineTemplate getBestRoutineForMyGoalType(UserInfo userInfo) {

        List<RoutineTemplate> rtSorted = this.findAll();

        Collections.sort(
                rtSorted,
                (routineTemplate1, routineTemplate2) -> {
                    if (routineTemplate1.getScoringSystem() == routineTemplate2.getScoringSystem()) {
                        return routineTemplate1.getName().compareTo(routineTemplate2.getName());
                    } else {
                        return routineTemplate1.getScoringSystem().compareTo(routineTemplate2.getScoringSystem());
                    }
                }
        );

        List<RoutineTemplate> rtFiltered = rtSorted
                .stream()
                .filter(routine -> routine.getGoalType().equals(userInfo.getUserGoal().getType()))
                .collect(Collectors.toList());

        if (rtSorted.size() == 0) {
            return rtSorted.get(0);
        } else {
            return rtFiltered.get(0);
        }
    }


    public RoutineTemplate createBestRoutineForMyGoalType(UserInfo userInfo) {

        return null;
    }
}
