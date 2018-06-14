package ar.edu.davinci.domain.model;

import ar.edu.davinci.domain.FitmeDomain;
import ar.edu.davinci.dto.nutrition.NutritionRequestDTO;
import ar.edu.davinci.dto.routineTemplate.RoutineTemplateRequestDTO;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
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

    public RoutineTemplate(Set<Exercise> exercices, Set<Nutrition> nutritions) {
        this.exercices = exercices;
        this.nutritions = nutritions;
    }

    public RoutineTemplate(Long id, Set<Exercise> exercices, Set<Nutrition> nutritions) {
        this.id = id;
        this.exercices = exercices;
        this.nutritions = nutritions;
    }
}