package domain.model;

import domain.FitmeDomain;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
@Table(name = "public.routine")
public class Routine extends FitmeDomain<Long> {

    @Id
    @Column(name = "routine_id")
    private Long id;

}