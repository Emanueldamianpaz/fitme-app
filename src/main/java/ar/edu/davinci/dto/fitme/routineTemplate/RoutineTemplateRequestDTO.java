package ar.edu.davinci.dto.fitme.routineTemplate;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class RoutineTemplateRequestDTO {

    private List<String> nutritions;
    private List<String> exercises;
}
