package ar.edu.davinci.domain.model.routine.detail;

import ar.edu.davinci.domain.FitmeEntity;
import ar.edu.davinci.domain.types.MealNutritionType;
import ar.edu.davinci.domain.dto.fitme.nutrition.NutritionRequestDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
@Table(name = "meal_nutrition")
public class MealNutrition extends FitmeEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private MealNutritionType type;

    @Column(name = "calories")
    private Double calories;

    public MealNutrition(NutritionRequestDTO nutrition) {
        this.name = nutrition.getName();
        this.type = nutrition.getType();
        this.calories = nutrition.getCalories();
    }

    public MealNutrition(Long id, NutritionRequestDTO nutrition) {
        this.id = id;
        this.name = nutrition.getName();
        this.type = nutrition.getType();
        this.calories = nutrition.getCalories();
    }
}
