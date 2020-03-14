package ar.edu.davinci.dto.fitme.user;

import ar.edu.davinci.domain.model.Goal;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserInfoRequestDTO {

    private Double initialWeight;
    private String height;
    private Double currentFat;
    private String frecuencyExercise;
    private Goal goal;
}
