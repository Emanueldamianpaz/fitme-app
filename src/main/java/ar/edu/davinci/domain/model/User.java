package ar.edu.davinci.domain.model;

import ar.edu.davinci.domain.FitmeDomain;
import ar.edu.davinci.infraestructure.security.util.FitmeUser;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
@Table(name = "user_entity", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User extends FitmeDomain<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "idUserFit")
    private String idUserFit;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private UserInfo userInfo;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "nickname")
    private String nickname;

    @OneToOne
    @JoinColumn(name = "id")
    private UserRoutine userRoutine;

    public User(FitmeUser user) {
        this.idUserFit = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }
}