package ar.edu.davinci.domain.dto.fitme.exercise_session;

import ar.edu.davinci.domain.types.ScoringType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@AllArgsConstructor
@Data
public class ExerciseRunningSession {

    private String id;
    private Timestamp timestamp;
    private ScoringType scoring;
    private RunningSessionDTO runningSession;
}
