package ar.edu.davinci.domain.model.user.detail;

import ar.edu.davinci.domain.FitmeEntity;
import ar.edu.davinci.domain.types.GoalType;
import ar.edu.davinci.domain.dto.fitme.goal.GoalRequestDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
@Table(name = "goal")
public class UserGoal extends FitmeEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private GoalType type;

    @Column(name = "goal_fat")
    private Double goalFat;


    public UserGoal(GoalRequestDTO goalRequest) {
        this.type = goalRequest.getType();
        this.goalFat = Double.parseDouble(goalRequest.getGoalFat());
    }

    public UserGoal(Long id, GoalRequestDTO goalRequest) {
        this.id = id;
        this.type = goalRequest.getType();
        this.goalFat = Double.parseDouble(goalRequest.getGoalFat());
    }
}
