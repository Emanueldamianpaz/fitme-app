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

    @Column(name = "gender")
    private String gender;

    @OneToOne
    @JoinColumn(name = "id")
    private UserRoutine userRoutine;

    public User(FitmeUser user) {
        this.id = user.getId();
        this.userInfo = new UserInfo(user.getId());
        this.name = user.getName();
        this.email = user.getEmail();
        this.nickname = user.getNickname();

    }
}