package ar.edu.davinci.dto.goal;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GoalRequestDTO {

    private String goalFat;
    private String type;

}