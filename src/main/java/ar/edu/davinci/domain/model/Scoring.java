package ar.edu.davinci.domain.model;

import ar.edu.davinci.domain.FitmeDomain;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
@Table(name = "scoring")
public class Scoring extends FitmeDomain<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "scoring")
    private String scoring;

    @Column(name = "tip")
    private String tip;

    @OneToOne
    @JoinColumn(name = "id_scoring")
    private UserRoutine userRoutine;
}