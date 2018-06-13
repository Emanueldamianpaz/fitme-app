package ar.edu.davinci.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserInfoRequestDTO {

    private Double weight;
    private String height;
    private String genre;
    private String currentFat;
    private String frecuencyExercise;

}
