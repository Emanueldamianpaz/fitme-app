package ar.edu.davinci.domain.dto.fitme.exercise_session;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
public class RunningSessionDTO {

    private long totalTimeWalk;
    private float distanceCovered;
    private float pace;
    private float speedAvg;
}
