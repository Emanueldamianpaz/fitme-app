package ar.edu.davinci.domain.model;

import ar.edu.davinci.domain.FitmeDomain;
import ar.edu.davinci.dto.nutrition.NutritionRequestDTO;
import ar.edu.davinci.dto.routineTemplate.RoutineTemplateRequestDTO;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
@Table(name = "routine_template")
public class RoutineTemplate extends FitmeDomain<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToMany
    @JoinColumn(name = "id_exercise")
    private Set<Exercise> exercices;

    @ManyToMany
    @JoinColumn(name = "id_nutrition")
    private Set<Nutrition> nutritions;

    public RoutineTemplate(RoutineTemplateRequestDTO routineTemplate) {
        this.exercices = routineTemplate.getExercices();
        this.nutritions = routineTemplate.getNutritions();
    }

    public RoutineTemplate(Long id, RoutineTemplateRequestDTO routineTemplate) {
        this.id = id;
        this.exercices = routineTemplate.getExercices();
        this.nutritions = routineTemplate.getNutritions();
    }
}