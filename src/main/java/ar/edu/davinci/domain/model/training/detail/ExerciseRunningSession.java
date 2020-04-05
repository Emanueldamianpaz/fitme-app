package ar.edu.davinci.domain.model.training.detail;

import ar.edu.davinci.domain.types.ScoringType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

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


    public ExerciseRunningSession(ExerciseRunningSession exerciseRunningSession) {
        this.id = UUID.randomUUID().toString();
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.scoring = exerciseRunningSession.getScoring();
        this.runningSession = exerciseRunningSession.getRunningSession();
    }


}
