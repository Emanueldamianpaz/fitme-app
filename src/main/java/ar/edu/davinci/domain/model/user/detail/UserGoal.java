package ar.edu.davinci.domain.model.user.detail;

import ar.edu.davinci.domain.FitmeEntity;
import ar.edu.davinci.domain.types.GoalType;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
@Table(name = "user_goal")
public class UserGoal extends FitmeEntity<String> {

    @Id
    @Column(name = "id", columnDefinition = "varchar")
    private String id;

    @Column(name = "type")
    private GoalType type;

    @Column(name = "goal_fat")
    private Double goalFat;

    public UserGoal(String id) {
        this.id = id;
        this.type = GoalType.UNKNOWN;
        this.goalFat = 0.0;
    }

}
