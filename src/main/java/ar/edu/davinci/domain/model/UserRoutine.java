package ar.edu.davinci.domain.model;

import ar.edu.davinci.domain.FitmeDomain;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
@Table(name = "user_routine")
public class UserRoutine extends FitmeDomain<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_scoring")
    private Scoring scoring;

    @OneToMany
    private Set<Routine> routine;

    public UserRoutine(Scoring scoring, Set<Routine> routine) {
        this.scoring = scoring;
        this.routine = routine;
    }

    public void addRoutine(Set<Routine> routine) {
        this.routine = routine;
    }

}
