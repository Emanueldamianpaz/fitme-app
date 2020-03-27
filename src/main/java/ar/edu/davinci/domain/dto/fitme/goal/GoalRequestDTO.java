package ar.edu.davinci.domain.dto.fitme.goal;

import ar.edu.davinci.domain.types.GoalType;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GoalRequestDTO {

    private String goalFat;
    private GoalType type;

}
