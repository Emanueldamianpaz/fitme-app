package ar.edu.davinci.dao.user.detail;

import ar.edu.davinci.dao.FitmeService;
import ar.edu.davinci.dao.routine.RoutineTemplateService;
import ar.edu.davinci.domain.model.user.detail.UserExperience;
import ar.edu.davinci.domain.model.user.detail.UserRoutine;
import ar.edu.davinci.domain.types.ScoringType;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class UserRoutineService extends FitmeService<UserRoutine, UserRoutine> {
    private RoutineTemplateService routineTemplateService;


    @Inject
    public UserRoutineService(SessionFactory sessionFactory,
                              RoutineTemplateService routineTemplateService) {
        super(UserRoutine.class, sessionFactory);
        this.routineTemplateService = routineTemplateService;
    }


    public void checkScoringFromUserRoutine(UserRoutine userRoutine) {

        List<ScoringType> scoring = getUserExperiencesFromRoutineTemplate(userRoutine.getRoutineTemplate().getId())
                .stream()
                .map(ux -> ux.getScoring())
                .collect(Collectors.toList());

        List<ScoringType> scoringBad = scoring.stream()
                .filter(s -> s.equals(ScoringType.BAD)).collect(Collectors.toList());
        List<ScoringType> scoringRegular = scoring.stream()
                .filter(s -> s.equals(ScoringType.REGULAR)).collect(Collectors.toList());
        List<ScoringType> scoringGood = scoring.stream()
                .filter(s -> s.equals(ScoringType.GOOD)).collect(Collectors.toList());

        if (scoringBad.size() > 5) {
            log.info("UserExperience bad, setting userRoutineScoring in BAD");
            userRoutine.getRoutineTemplate().setScoringSystem(ScoringType.BAD);
        } else if (scoringRegular.size() > 5) {
            log.info("UserExperience regular, setting userRoutineScoring in REGULAR");
            userRoutine.getRoutineTemplate().setScoringSystem(ScoringType.REGULAR);
        } else if (scoringGood.size() > 5) {
            log.info("UserExperience god, setting userRoutineScoring in GOOD");
            userRoutine.getRoutineTemplate().setScoringSystem(ScoringType.GOOD);
        } else {
            log.info("No modification for scoring userRoutine");
        }

        routineTemplateService.update(userRoutine.getRoutineTemplate());

    }

    public List<UserExperience> getUserExperiencesFromRoutineTemplate(Long routineTemplateId) {

        List<UserExperience> userExperiencesFromRoutine = findAll()
                .stream()
                .filter(userRoutine -> userRoutine.getRoutineTemplate().getId().equals(routineTemplateId))
                .map(userRoutine -> Lists.newArrayList(userRoutine.getUserExperiences()))
                .flatMap(List::stream)
                .collect(Collectors.toList());

        return userExperiencesFromRoutine;
    }

}
