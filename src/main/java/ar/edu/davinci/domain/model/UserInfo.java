package ar.edu.davinci.domain.model;

import ar.edu.davinci.domain.FitmeDomain;
import lombok.*;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
@Table(name = "public.user_info")
public class UserInfo extends FitmeDomain<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "height")
    private String height;

    @Column(name = "genre")
    private String genre;

}