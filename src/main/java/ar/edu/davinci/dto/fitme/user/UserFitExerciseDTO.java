package ar.edu.davinci.dto.fitme.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserFitExerciseDTO {

    private String latitude;
    private String longitude;
    private String alture;
}
