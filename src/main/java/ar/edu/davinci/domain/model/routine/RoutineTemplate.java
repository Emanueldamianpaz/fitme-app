package ar.edu.davinci.domain.model.routine;

import ar.edu.davinci.domain.FitmeEntity;
import ar.edu.davinci.domain.model.routine.detail.MealNutrition;
import ar.edu.davinci.domain.model.routine.detail.WorkoutExercise;
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
    @JoinColumn(name = "id_exercise")
    private Set<WorkoutExercise> workoutExercises;

    @ManyToMany
    @JoinColumn(name = "id_nutrition")
    private Set<MealNutrition> mealNutritions;

    @Column(name = "scoring")
    private ScoringType scoringSystem; // Dado por el sistema


    public RoutineTemplate(String name, String description, ScoringType scoringSystem, Set<WorkoutExercise> workoutExercises, Set<MealNutrition> mealNutritions) {
        this.name = name;
        this.description = description;
        this.scoringSystem = scoringSystem;
        this.workoutExercises = workoutExercises;
        this.mealNutritions = mealNutritions;
        this.scoringSystem = scoringSystem;
    }

    public RoutineTemplate(Long id, String name, String description, ScoringType scoringSystem, Set<WorkoutExercise> workoutExercises, Set<MealNutrition> mealNutritions) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.scoringSystem = scoringSystem;
        this.workoutExercises = workoutExercises;
        this.mealNutritions = mealNutritions;
        this.scoringSystem = scoringSystem;
    }

}
