package ar.edu.davinci.domain.model;

import ar.edu.davinci.domain.FitmeDomain;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@AllArgsConstructor
@Builder
@Table(name = "exercise_session")
public class ExerciseSession extends FitmeDomain<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nutrition_info_raw")
    private String nutrition;

    @Column(name = "exercise_info_raw")
    private String exercise;

    public ExerciseSession(String exerciseSessionExercise, String exerciseSessionNutrition) {
        this.exercise = exerciseSessionExercise;
        this.nutrition = exerciseSessionNutrition;
    }
}