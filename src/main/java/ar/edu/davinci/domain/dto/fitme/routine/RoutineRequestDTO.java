package ar.edu.davinci.domain.dto.fitme.routine;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RoutineRequestDTO {

    private String name;
    private String description;
    private Long routineTemplate;
}
