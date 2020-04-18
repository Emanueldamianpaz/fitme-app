package ar.edu.davinci.domain.model.user.detail;

import ar.edu.davinci.domain.FitmeEntity;
import ar.edu.davinci.domain.types.GoalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Data
@Builder
@Table(name = "user_goal")
public class UserGoal extends FitmeEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private GoalType type;

    @Column(name = "goal_fat")
    private Double goalFat;


    public UserGoal() {
        this.type = GoalType.UNKNOWN;
        this.goalFat = 0.0;
    }

}
