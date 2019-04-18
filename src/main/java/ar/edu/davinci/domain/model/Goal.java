package ar.edu.davinci.domain.model;

import ar.edu.davinci.domain.FitmeDomain;
import ar.edu.davinci.dto.fitme.goal.GoalRequestDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
@Table(name = "goal")
public class Goal extends FitmeDomain<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "goal_fat")
    private Double goalFat;


    public Goal(GoalRequestDTO goalRequest) {
        this.type = goalRequest.getType();
        this.goalFat = Double.parseDouble(goalRequest.getGoalFat());
    }

    public Goal(Long id, GoalRequestDTO goalRequest) {
        this.id = id;
        this.type = goalRequest.getType();
        this.goalFat = Double.parseDouble(goalRequest.getGoalFat());
    }
}