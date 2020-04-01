package ar.edu.davinci.domain.model.routine.detail;

import ar.edu.davinci.domain.FitmeEntity;
import ar.edu.davinci.domain.types.DifficultyType;
import ar.edu.davinci.domain.types.WorkoutExerciseType;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
@Table(name = "workout_exercise")
public class WorkoutExercise extends FitmeEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private WorkoutExerciseType type;

    @Column(name = "description")
    private String description;

    @Column(name = "difficulty")
    private DifficultyType difficulty;

    public WorkoutExercise(Long id, WorkoutExercise workoutExercise) {
        this.id = id;
        this.name = workoutExercise.getName();
        this.type = workoutExercise.getType();
        this.description = workoutExercise.getDescription();
        this.difficulty = workoutExercise.getDifficulty();
    }
}
