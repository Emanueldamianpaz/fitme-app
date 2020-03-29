package ar.edu.davinci.domain.model.training;

import ar.edu.davinci.domain.FitmeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@AllArgsConstructor
@Builder
@Table(name = "training_session")
public class TrainingSession extends FitmeEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "meal_nutrition_info_raw", columnDefinition = "TEXT")
    private String mealNutrition;

    @Column(name = "workout_exercise_info_raw", columnDefinition = "TEXT")
    private String workoutExercise;

    public TrainingSession(String workoutExercise, String mealNutrition) {
        this.workoutExercise = workoutExercise;
        this.mealNutrition = mealNutrition;
    }
}
