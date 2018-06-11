package ar.edu.davinci.domain.model;

import ar.edu.davinci.domain.FitmeDomain;
import ar.edu.davinci.dto.nutrition.NutritionRequestDTO;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
@Table(name = "public.routine_template")
public class RoutineTemplate extends FitmeDomain<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @OneToOne
    @Column(name = "id_routine")
    private Routine routine;

    @OneToMany
    @Column(name = "id_exercise")
    private List<Exercise> exercices;

    @OneToMany
    @Column(name = "id_nutrition")
    private List<Nutrition> nutritions;

   
}