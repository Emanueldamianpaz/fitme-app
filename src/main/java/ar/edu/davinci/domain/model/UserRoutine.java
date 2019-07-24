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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_routine")
    private Routine routine;



}