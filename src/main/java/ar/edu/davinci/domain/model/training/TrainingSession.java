package ar.edu.davinci.domain.model.training;

import ar.edu.davinci.domain.FitmeEntity;
import ar.edu.davinci.domain.model.training.detail.ExerciseRunningSession;
import ar.edu.davinci.domain.model.training.detail.NutritionSession;
import ar.edu.davinci.infraestructure.converters.ExerciseRunningSessionConverterJson;
import ar.edu.davinci.infraestructure.converters.NutritionSessionConverterJson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    @Column(name = "date")
    private String date;

    @Column(name = "nutrition_session__info_raw", columnDefinition = "TEXT")
    @Convert(converter = NutritionSessionConverterJson.class)
    private List<NutritionSession> nutritionSessions;

    @Column(name = "exercise_running_session_info_raw", columnDefinition = "TEXT")
    @Convert(converter = ExerciseRunningSessionConverterJson.class)
    private List<ExerciseRunningSession> runningSessions;

    public TrainingSession() {

        this.date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
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
