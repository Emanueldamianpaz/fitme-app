package ar.edu.davinci.domain.model;

import ar.edu.davinci.domain.FitmeDomain;
import ar.edu.davinci.dto.fitme.user.UserFitExerciseDTO;
import ar.edu.davinci.dto.fitme.user.UserFitNutritionDTO;
import ar.edu.davinci.utils.JsonTransformer;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
@Table(name = "user_fit")
public class UserFit extends FitmeDomain<Long> {

    @Id
    private Long id;

    @Column(name = "nutrition_info_raw")
    private String nutrition;

    @Column(name = "running_raw")
    private String running;

    @Column(name = "exercise_info_raw")
    private String exercise;

    public UserFit(String userFitExercise, String userFitNutrition) {
        this.exercise = userFitExercise;
        this.nutrition = userFitNutrition;
    }

    public UserFit(Long id, String userFitExercise, String userFitNutrition) {
        this.id = id;
        this.exercise = userFitExercise;
        this.nutrition = userFitNutrition;
    }


}