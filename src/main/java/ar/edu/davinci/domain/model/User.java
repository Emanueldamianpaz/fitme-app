package ar.edu.davinci.domain.model;

import ar.edu.davinci.domain.FitmeDomain;
import ar.edu.davinci.infraestructure.security.roles.FitmeRoles;
import ar.edu.davinci.infraestructure.security.util.FitmeUser;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
@Table(name = "user_entity", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User extends FitmeDomain<String> {

    @Id
    @Column(name = "id")
    private String id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private UserInfo userInfo;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "picture")
    private String picture;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "genre")
    private String genre;

    @Column(name = "role")
    private String role;

    @OneToOne
    private UserRoutine userRoutine;

    public User(FitmeUser user, UserInfo userInfo, FitmeRoles role, UserRoutine userRoutine) {
        this.id = user.getId();
        this.userInfo = userInfo;
        this.name = user.getName();
        this.lastName = user.getLast_name();
        this.genre = user.getGenre();
        this.picture = user.getPicture();
        this.role = role.name();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.userRoutine = userRoutine;
    }
}
