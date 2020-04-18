package ar.edu.davinci.domain.model.user.detail;

import ar.edu.davinci.domain.FitmeEntity;
import ar.edu.davinci.domain.dto.fitme.user.UserInfoRequestDTO;
import ar.edu.davinci.domain.model.training.TrainingSession;
import ar.edu.davinci.domain.model.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@Data
@Builder
@Table(name = "user_info")
public class UserInfo extends FitmeEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "initial_weight")
    private Double initialWeight;

    @Column(name = "height")
    private String height;

    @Column(name = "current_fat")
    private Double currentFat;

    @Column(name = "frecuency_exercise")
    private String frecuencyExercise;

    @OneToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false)
    private UserGoal userGoal;

    @OneToMany
    @JoinColumn(name = "id_user_fit", referencedColumnName = "id")
    private Set<TrainingSession> trainingSession;

    public UserInfo() {
        this.userGoal = new UserGoal();
    }

    public UserInfo(UserInfoRequestDTO userInfoRequest) {
        this.initialWeight = userInfoRequest.getInitialWeight();
        this.height = userInfoRequest.getHeight();
        this.currentFat = userInfoRequest.getCurrentFat();
        this.frecuencyExercise = userInfoRequest.getFrecuencyExercise();
    }

    public UserInfo(Long id, UserInfoRequestDTO userInfoRequest) {
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
