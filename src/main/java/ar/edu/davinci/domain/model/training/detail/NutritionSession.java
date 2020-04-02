package ar.edu.davinci.domain.model.training.detail;

import ar.edu.davinci.domain.types.MealNutritionType;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
public class NutritionSession implements Serializable {
    private String id;
    private Timestamp timestamp;
    private String name;
    private MealNutritionType type;
    private Double calories;
}
