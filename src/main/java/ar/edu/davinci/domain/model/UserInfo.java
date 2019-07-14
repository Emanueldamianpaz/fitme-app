package ar.edu.davinci.domain.model;

import ar.edu.davinci.domain.FitmeDomain;
import ar.edu.davinci.dto.fitme.user.UserInfoRequestDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
@Table(name = "user_info")
public class UserInfo extends FitmeDomain<String> {

    @Id
    private String id;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "height")
    private String height;

    @Column(name = "current_fat")
    private Double currentFat;

    @Column(name = "frecuency_exercise")
    private String frecuencyExercise;

    @OneToOne
    @JoinColumn(name = "id_goal", referencedColumnName = "id")
    private Goal goal;

    @OneToMany
    @JoinColumn(name = "id_user_fit", referencedColumnName = "id")
    private Set<ExerciseSession> exerciseSession;

    public UserInfo(String id) {
        this.id = id;
    }

    public UserInfo(UserInfoRequestDTO userInfoRequest) {
        this.weight = userInfoRequest.getWeight();
        this.height = userInfoRequest.getHeight();
        this.currentFat = Double.parseDouble(userInfoRequest.getCurrentFat());
        this.frecuencyExercise = userInfoRequest.getFrecuencyExercise();
    }

    public UserInfo(String id, UserInfoRequestDTO userInfoRequest) {
        this.id = id;
        this.weight = userInfoRequest.getWeight();
        this.height = userInfoRequest.getHeight();
        this.currentFat = Double.parseDouble(userInfoRequest.getCurrentFat());
        this.frecuencyExercise = userInfoRequest.getFrecuencyExercise();
    }

    public void addExerciseSession(ExerciseSession exerciseSession) {
        this.exerciseSession.add(exerciseSession);
    }
}