package ar.edu.davinci.domain.dto.fitme.scoring;

import ar.edu.davinci.domain.types.ScoringType;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserExperienceDTO {

    private ScoringType scoring;
    private String coachTip;
}
