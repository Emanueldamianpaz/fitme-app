package ar.edu.davinci.dto.exercise;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ExerciseRequestDTO {

    private String name;
    private String type;
    private String difficulty;
    private String description;

}
