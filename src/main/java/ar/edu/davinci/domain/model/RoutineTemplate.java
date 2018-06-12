package ar.edu.davinci.domain.model;

import ar.edu.davinci.domain.FitmeDomain;
import ar.edu.davinci.dto.nutrition.NutritionRequestDTO;
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

    @OneToOne
    @JoinColumn(name = "id_routine_template")
    private Routine routine;

    @ManyToMany(mappedBy = "routineTemplate")
    private Set<Exercise> exercices;

    @ManyToMany(mappedBy = "routineTemplate")
    private Set<Nutrition> nutritions;


}