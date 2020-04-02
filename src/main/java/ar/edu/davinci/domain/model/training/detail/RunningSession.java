package ar.edu.davinci.domain.model.training.detail;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
public class RunningSession {

    private long totalTimeWalk;
    private float distanceCovered;
    private float pace;
    private float speedAvg;
}
