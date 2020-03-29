package ar.edu.davinci.domain.model.user.detail;

import ar.edu.davinci.domain.FitmeEntity;
import ar.edu.davinci.domain.types.ScoringType;
import ar.edu.davinci.domain.dto.fitme.scoring.UserExperienceDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
@Table(name = "scoring")
public class UserExperience extends FitmeEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "scoring")
    private ScoringType scoring;

    @Column(name = "coachTip")
    private String coachTip;

    public UserExperience(UserExperienceDTO scoring) {
        this.scoring = scoring.getScoring();
        this.coachTip = scoring.getCoachTip();
    }

    public UserExperience(ScoringType scoring, String coachTip) {
        this.scoring = scoring;
        this.coachTip = coachTip;
    }

    public UserExperience(Long id, UserExperienceDTO scoring) {
        this.id = id;
        this.scoring = scoring.getScoring();
        this.coachTip = scoring.getCoachTip();
    }
}
