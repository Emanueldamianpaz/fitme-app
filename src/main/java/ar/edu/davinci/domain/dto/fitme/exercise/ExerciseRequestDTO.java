package ar.edu.davinci.domain.dto.fitme.exercise;

import ar.edu.davinci.domain.types.DifficultyType;
import ar.edu.davinci.domain.types.WorkoutExerciseType;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ExerciseRequestDTO {

    private String name;
    private WorkoutExerciseType type;
    private DifficultyType difficulty;
    private String description;

}
