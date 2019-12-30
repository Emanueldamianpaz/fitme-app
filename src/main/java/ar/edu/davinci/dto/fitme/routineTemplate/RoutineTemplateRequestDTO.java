package ar.edu.davinci.dto.fitme.routineTemplate;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class RoutineTemplateRequestDTO {

    private List<Long> nutritions;
    private List<Long> exercises;
}
