package ar.edu.davinci.domain.model.user.detail;

import ar.edu.davinci.domain.FitmeEntity;
import ar.edu.davinci.domain.model.routine.RoutineTemplate;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
@Table(name = "user_routine")
public class UserRoutine extends FitmeEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany
    @JoinColumn(name = "id_user_experience")
    private Set<UserExperience> userExperiences;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_routine_template")
    private RoutineTemplate routineTemplate;

    public UserRoutine(RoutineTemplate routineTemplate) {
        this.routineTemplate = routineTemplate;
    }

    public UserRoutine(Long id, RoutineTemplate routineTemplate) {
        this.id = id;
        this.routineTemplate = routineTemplate;
    }

    public void addUserExperience(UserExperience userExperience) {
        this.userExperiences.add(userExperience);
    }
}
