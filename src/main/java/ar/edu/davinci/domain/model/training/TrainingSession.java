package ar.edu.davinci.domain.model.training;

import ar.edu.davinci.domain.FitmeEntity;
import ar.edu.davinci.domain.model.training.detail.ExerciseRunningSession;
import ar.edu.davinci.domain.model.training.detail.NutritionSession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Builder
@Table(name = "training_session")
public class TrainingSession extends FitmeEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nutrition_session__info_raw", columnDefinition = "TEXT")
    @Type(type = "serializable")
    private List<NutritionSession> nutritionSessions;

    @Column(name = "exercise_running_session_info_raw", columnDefinition = "TEXT")
    @Type(type = "serializable")
    private List<ExerciseRunningSession> runningSessions;

    public TrainingSession() {
        this.runningSessions = new ArrayList<>();
        this.nutritionSessions = new ArrayList<>();
    }

    public TrainingSession(List<ExerciseRunningSession> runningSessions, List<NutritionSession> nutritionSessions) {
        this.runningSessions = runningSessions;
        this.nutritionSessions = nutritionSessions;
    }

    public void addExerciseRunningSession(ExerciseRunningSession runningSession) {
        this.runningSessions.add(runningSession);
    }

    public void addNutritionSession(NutritionSession nutritionSession) {
        this.nutritionSessions.add(nutritionSession);
    }
}
