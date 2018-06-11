package ar.edu.davinci.domain.model;

import ar.edu.davinci.domain.FitmeDomain;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
@Table(name = "public.user")
public class User extends FitmeDomain<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_session")
    private String idSession;

}