package ar.edu.davinci.dto.nutrition;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class NutritionRequestDTO {

    private String name;
    private String type;
    private Double calories;

}
