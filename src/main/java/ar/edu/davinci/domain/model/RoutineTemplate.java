package ar.edu.davinci.domain.model;

import ar.edu.davinci.domain.FitmeDomain;
import lombok.*;

import javax.persistence.*;
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
    private Set<Exercise> exercises;

    @ManyToMany
    @JoinColumn(name = "id_nutrition")
    private Set<Nutrition> nutritions;

    @Column(name = "scoring")
    private String scoring;


    public RoutineTemplate(Set<Exercise> exercises, Set<Nutrition> nutritions, String scoring) {
        this.exercises = exercises;
        this.nutritions = nutritions;
        this.scoring = scoring;
    }

    public RoutineTemplate(Long id, Set<Exercise> exercises, Set<Nutrition> nutritions) {
        this.id = id;
        this.exercises = exercises;
        this.nutritions = nutritions;
    }


}