package ar.edu.davinci.domain.model.routine.detail;

import ar.edu.davinci.domain.FitmeEntity;
import ar.edu.davinci.domain.types.MealNutritionType;
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

    public MealNutrition(Long id, MealNutrition mealNutrition) {
        this.id = id;
        this.name = mealNutrition.getName();
        this.type = mealNutrition.getType();
        this.calories = mealNutrition.getCalories();
    }
}
