package ar.edu.davinci.domain.model;

import ar.edu.davinci.domain.FitmeDomain;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
@Table(name = "public.goal")
public class Goal extends FitmeDomain<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "current_fat")
    private String currentFat;

    @Column(name = "goal_fat")
    private String goalFat;

    @Column(name = "frecuency_exercise")
    private String frecuencyExercise;

    @OneToOne
    @JoinColumn(name="id_goal")
    private UserInfo userInfo;

}