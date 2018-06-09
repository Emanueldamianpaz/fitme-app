package ar.edu.davinci.domain.model;

import ar.edu.davinci.domain.FitmeDomain;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
@Table(name = "public.scoring")
public class Scoring extends FitmeDomain<Long> {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "scoring")
    private String scoring;

    @Column(name = "tip")
    private String tip;

}