package ar.edu.davinci.dto.routine;

import ar.edu.davinci.domain.model.RoutineTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RoutineRequestDTO {

    private String name;
    private String description;
    private RoutineTemplate routineTemplate;
}