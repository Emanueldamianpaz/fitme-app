package ar.edu.davinci.domain.model.user;

import ar.edu.davinci.domain.FitmeEntity;
import ar.edu.davinci.domain.FitmeRoles;
import ar.edu.davinci.domain.model.user.detail.UserInfo;
import ar.edu.davinci.domain.model.user.detail.UserRoutine;
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

    @OneToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id", referencedColumnName = "id")
    private UserInfo userInfo;

    @OneToMany
    @JoinColumn(name = "userroutine_id", referencedColumnName = "id")
    private Set<UserRoutine> userRoutines;

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

    public UserEntity(FitmeUser user,  FitmeRoles role) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastName = user.getLast_name();
        this.genre = user.getGenre();
        this.picture = user.getPicture();
        this.role = role.name();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }

    public void removeUserRoutine(UserRoutine userRoutine) {
        if (this.userRoutines.contains(userRoutine)) {
            this.userRoutines.remove(userRoutine);
        }
    }

    public void addUserRoutine(UserRoutine userRoutine) {
        if (!this.userRoutines.contains(userRoutine)) {
            this.userRoutines.add(userRoutine);
        }
    }
}
