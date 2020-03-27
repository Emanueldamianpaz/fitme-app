package ar.edu.davinci.domain.model.user.detail;

import ar.edu.davinci.domain.FitmeEntity;
import ar.edu.davinci.domain.model.routine.RoutineTemplate;
import ar.edu.davinci.domain.dto.fitme.routine.RoutineRequestDTO;
import lombok.*;

import javax.persistence.*;

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

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "id_scoring")
    private UserExperience userExperience;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_routine_template")
    private RoutineTemplate routineTemplate;

    public UserRoutine(RoutineRequestDTO routine, RoutineTemplate routineTemplate) {
        this.name = routine.getName();
        this.description = routine.getDescription();
        this.routineTemplate = routineTemplate;
    }

    public UserRoutine(Long id, RoutineRequestDTO routine, RoutineTemplate routineTemplate) {
        this.id = id;
        this.name = routine.getName();
        this.description = routine.getDescription();
        this.routineTemplate = routineTemplate;
    }

}
