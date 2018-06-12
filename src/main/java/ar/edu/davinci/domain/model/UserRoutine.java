package ar.edu.davinci.domain.model;

import ar.edu.davinci.domain.FitmeDomain;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @OneToOne(mappedBy = "userRoutine")
    private User user;

    @OneToOne(mappedBy = "userRoutine")
    private Scoring scoring;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user_routine")
    private Routine routine;


}