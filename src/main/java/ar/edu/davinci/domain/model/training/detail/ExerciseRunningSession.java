package ar.edu.davinci.domain.model.training.detail;

import ar.edu.davinci.domain.types.ScoringType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@AllArgsConstructor
@Data
@Builder
public class ExerciseRunningSession implements Serializable {
    private String id;
    private Timestamp timestamp;
    private ScoringType scoring;
    private RunningSession runningSession;

    public ExerciseRunningSession(ScoringType scoring, RunningSession runningSession) {
        this.scoring = scoring;
        this.runningSession = runningSession;
    }
}
