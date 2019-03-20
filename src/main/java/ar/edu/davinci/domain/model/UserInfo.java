package ar.edu.davinci.domain.model;

import ar.edu.davinci.domain.FitmeDomain;
import ar.edu.davinci.dto.fitme.user.UserInfoRequestDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
@Table(name = "user_info")
public class UserInfo extends FitmeDomain<Long> {

    @Id
    private Long id;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "height")
    private String height;

    @Column(name = "current_fat")
    private String currentFat;

    @Column(name = "frecuency_exercise")
    private String frecuencyExercise;

    @OneToOne
    @JoinColumn(name = "id_goal", referencedColumnName = "id")
    private Goal goal;

    @OneToOne
    @JoinColumn(name = "id_user_fit", referencedColumnName = "id")
    private UserFit userFit;

    public UserInfo(UserInfoRequestDTO userInfoRequest) {
        this.weight = userInfoRequest.getWeight();
        this.height = userInfoRequest.getHeight();
        this.currentFat = userInfoRequest.getCurrentFat();
        this.frecuencyExercise = userInfoRequest.getFrecuencyExercise();
    }

    public UserInfo(Long id, UserInfoRequestDTO userInfoRequest) {
        this.id = id;
        this.weight = userInfoRequest.getWeight();
        this.height = userInfoRequest.getHeight();
        this.currentFat = userInfoRequest.getCurrentFat();
        this.frecuencyExercise = userInfoRequest.getFrecuencyExercise();
    }

    public UserInfo(UserInfoRequestDTO userInfoRequest, UserFit userFit) {
        this.weight = userInfoRequest.getWeight();
        this.height = userInfoRequest.getHeight();
        this.currentFat = userInfoRequest.getCurrentFat();
        this.frecuencyExercise = userInfoRequest.getFrecuencyExercise();
        this.userFit = userFit;

    }
}