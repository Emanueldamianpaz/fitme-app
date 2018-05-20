package domain.model;

import domain.FitmeDomain;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "public.routines")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
public class RoutineObject extends FitmeDomain<Long> {

    @Id
    @Column(name = "routine_id")
    private Long id;

}