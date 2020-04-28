package ar.edu.davinci.domain.dto.fitme.trainning;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class TrainningTotalStadistDTO {

    private String date;
    private Double calories;
    private Double meters;
}
