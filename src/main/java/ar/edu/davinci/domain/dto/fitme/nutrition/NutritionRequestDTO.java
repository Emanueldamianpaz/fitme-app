package ar.edu.davinci.domain.dto.fitme.nutrition;

import ar.edu.davinci.domain.types.MealNutritionType;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class NutritionRequestDTO {

    private String name;
    private MealNutritionType type;
    private Double calories;

}
