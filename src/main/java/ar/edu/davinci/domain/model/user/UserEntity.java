package ar.edu.davinci.domain.model.user;

import ar.edu.davinci.domain.FitmeEntity;
import ar.edu.davinci.domain.model.user.detail.UserInfo;
import ar.edu.davinci.domain.model.user.detail.UserRoutine;
import ar.edu.davinci.domain.FitmeRoles;
import ar.edu.davinci.infraestructure.security.session.FitmeUser;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
@Table(name = "user_entity")
public class UserEntity extends FitmeEntity<String> {

    @Id
    @Column(name = "id")
    private String id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private UserInfo userInfo;

    @ManyToMany
    @JoinColumn(name = "id_user_routine")
    private Set<UserRoutine> userRoutine;

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

    public UserEntity(FitmeUser user, UserInfo userInfo, FitmeRoles role) {
        this.id = user.getId();
        this.userInfo = userInfo;
        this.name = user.getName();
        this.lastName = user.getLast_name();
        this.genre = user.getGenre();
        this.picture = user.getPicture();
        this.role = role.name();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }

    public void removeUserRoutine(UserRoutine userRoutine) {
        if (this.userRoutine.contains(userRoutine)) {
            this.userRoutine.remove(userRoutine);
        }
    }

    public void addUserRoutine(UserRoutine userRoutine) {
        if (!this.userRoutine.contains(userRoutine)) {
            this.userRoutine.add(userRoutine);
        }
    }
}
