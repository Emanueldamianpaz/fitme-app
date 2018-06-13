package ar.edu.davinci.dto.routineTemplate;

import ar.edu.davinci.domain.model.Exercise;
import ar.edu.davinci.domain.model.Nutrition;
import ar.edu.davinci.domain.model.RoutineTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
public class RoutineTemplateRequestDTO {

    private Set<Nutrition> nutritions;
    private Set<Exercise> exercices;
}
