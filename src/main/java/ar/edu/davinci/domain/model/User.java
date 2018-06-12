package ar.edu.davinci.domain.model;

import ar.edu.davinci.domain.FitmeDomain;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
@Table(name = "user_entity")
public class User extends FitmeDomain<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_session")
    private String idSession;

    @OneToOne(mappedBy = "user")
    private UserInfo userInfo;

    @OneToOne
    @JoinColumn(name ="id_user")
    private UserRoutine userRoutine;

}