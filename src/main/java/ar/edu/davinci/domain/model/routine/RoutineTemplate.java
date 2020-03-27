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

    @ManyToMany
    @JoinColumn(name = "id_exercise")
    private Set<WorkoutExercise> workoutExercises;

    @ManyToMany
    @JoinColumn(name = "id_nutrition")
    private Set<MealNutrition> mealNutritions;

    @Column(name = "scoring")
    private ScoringType scoring;


    public RoutineTemplate(Set<WorkoutExercise> workoutExercises, Set<MealNutrition> mealNutritions, ScoringType scoring) {
        this.workoutExercises = workoutExercises;
        this.mealNutritions = mealNutritions;
        this.scoring = scoring;
    }

    public RoutineTemplate(Long id, Set<WorkoutExercise> workoutExercises, Set<MealNutrition> mealNutritions) {
        this.id = id;
        this.workoutExercises = workoutExercises;
        this.mealNutritions = mealNutritions;
    }


}
