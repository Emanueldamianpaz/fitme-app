package ar.edu.davinci.domain.model;

import ar.edu.davinci.domain.FitmeDomain;
import ar.edu.davinci.dto.nutrition.NutritionRequestDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
@Table(name = "nutrition")
public class Nutrition extends FitmeDomain<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "calories")
    private Double calories;

    public Nutrition(NutritionRequestDTO nutrition) {
        this.name = nutrition.getName();
        this.type = nutrition.getType();
        this.calories = nutrition.getCalories();
    }

    public Nutrition(Long id, NutritionRequestDTO nutrition) {
        this.id = id;
        this.name = nutrition.getName();
        this.type = nutrition.getType();
        this.calories = nutrition.getCalories();
    }
}