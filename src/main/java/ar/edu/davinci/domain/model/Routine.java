package ar.edu.davinci.domain.model;

import ar.edu.davinci.domain.FitmeDomain;
import ar.edu.davinci.dto.fitme.routine.RoutineRequestDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
@Table(name = "routine")
public class Routine extends FitmeDomain<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_routine_template")
    private RoutineTemplate routineTemplate;

    public Routine(RoutineRequestDTO routine, RoutineTemplate routineTemplate) {
        this.name = routine.getName();
        this.description = routine.getDescription();
        this.routineTemplate = routineTemplate;
    }

    public Routine(Long id, RoutineRequestDTO routine, RoutineTemplate routineTemplate) {
        this.id = id;
        this.name = routine.getName();
        this.description = routine.getDescription();
        this.routineTemplate = routineTemplate;
    }
}
