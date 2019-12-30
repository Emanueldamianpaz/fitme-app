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
public class UserRoutine extends FitmeDomain<String> {

    @Id
    @Column(name = "id")
    private String id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @OneToOne
    @JoinColumn(name = "id_scoring")
    private Scoring scoring;

    @OneToMany
    @JoinColumn(name = "id_routine")
    private Set<Routine> routine;

    public UserRoutine(User user, Scoring scoring, Set<Routine> routine) {
        this.id = user.getId();
        this.user = user;
        this.scoring = scoring;
        this.routine = routine;
    }


}
