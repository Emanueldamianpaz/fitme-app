package ar.edu.davinci.domain.model.training;

import ar.edu.davinci.domain.FitmeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@AllArgsConstructor
@Builder
@Table(name = "exercise_session")
public class TrainingSession extends FitmeEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nutrition_info_raw", columnDefinition = "TEXT")
    private String nutritionSession;

    @Column(name = "exercise_info_raw", columnDefinition = "TEXT")
    private String exerciseSession;

    public TrainingSession(String exerciseSession, String nutritionSession) {
        this.exerciseSession = exerciseSession;
        this.nutritionSession = nutritionSession;
    }
}
