package ar.edu.davinci.domain.model.user.detail;

import ar.edu.davinci.domain.FitmeEntity;
import ar.edu.davinci.domain.model.training.TrainingSession;
import ar.edu.davinci.domain.dto.fitme.user.UserInfoRequestDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
@Table(name = "user_info")
public class UserInfo extends FitmeEntity<String> {

    @Id
    private String id;

    @Column(name = "initial_weight")
    private Double initialWeight;

    @Column(name = "height")
    private String height;

    @Column(name = "current_fat")
    private Double currentFat;

    @Column(name = "frecuency_exercise")
    private String frecuencyExercise;

    @OneToOne
    @JoinColumn(name = "id_goal", referencedColumnName = "id")
    private UserGoal userGoal;

    @OneToMany
    @JoinColumn(name = "id_user_fit", referencedColumnName = "id")
    private Set<TrainingSession> trainingSession;

    public UserInfo(String id) {
        this.id = id;
    }

    public UserInfo(UserInfoRequestDTO userInfoRequest) {
        this.initialWeight = userInfoRequest.getInitialWeight();
        this.height = userInfoRequest.getHeight();
        this.currentFat = userInfoRequest.getCurrentFat();
        this.frecuencyExercise = userInfoRequest.getFrecuencyExercise();
    }

    public UserInfo(String id, UserInfoRequestDTO userInfoRequest) {
        this.id = id;
        this.initialWeight = userInfoRequest.getInitialWeight();
        this.height = userInfoRequest.getHeight();
        this.currentFat = userInfoRequest.getCurrentFat();
        this.frecuencyExercise = userInfoRequest.getFrecuencyExercise();
    }

    public void addExerciseSession(TrainingSession trainingSession) {
        this.trainingSession.add(trainingSession);
    }
}
