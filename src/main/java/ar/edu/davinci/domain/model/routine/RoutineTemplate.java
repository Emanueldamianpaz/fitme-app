package ar.edu.davinci.domain.model.routine;

import ar.edu.davinci.domain.FitmeEntity;
import ar.edu.davinci.domain.model.routine.detail.MealNutrition;
import ar.edu.davinci.domain.model.routine.detail.WorkoutExercise;
import ar.edu.davinci.domain.types.GoalType;
import ar.edu.davinci.domain.types.ScoringType;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
@Table(name = "routine_template")
public class RoutineTemplate extends FitmeEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinColumn(name = "workoutexercise_id")
    private Set<WorkoutExercise> workoutExercises;

    @ManyToMany
    @JoinColumn(name = "mealnutrition_id")
    private Set<MealNutrition> mealNutritions;

    @Column(name = "scoring")
    private ScoringType scoringSystem; // Dado por el sistema

    @Column(name = "goal_type")
    private GoalType goalType;

    public RoutineTemplate(String name, String description, ScoringType scoringSystem, Set<WorkoutExercise> workoutExercises, Set<MealNutrition> mealNutritions, GoalType goalType) {
        this.name = name;
        this.description = description;
        this.scoringSystem = scoringSystem;
        this.workoutExercises = workoutExercises;
        this.mealNutritions = mealNutritions;
        this.scoringSystem = scoringSystem == null ? ScoringType.UNKNOWN : scoringSystem;
        this.goalType = goalType;
    }

    public RoutineTemplate(Long id, String name, String description, ScoringType scoringSystem, Set<WorkoutExercise> workoutExercises, Set<MealNutrition> mealNutritions, GoalType goalType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.scoringSystem = scoringSystem;
        this.workoutExercises = workoutExercises;
        this.mealNutritions = mealNutritions;
        this.scoringSystem = scoringSystem == null ? ScoringType.UNKNOWN : scoringSystem;
        this.goalType = goalType;
    }

}
