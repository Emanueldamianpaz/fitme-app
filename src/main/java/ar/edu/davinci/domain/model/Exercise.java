package ar.edu.davinci.domain.model;

import ar.edu.davinci.domain.FitmeDomain;
import ar.edu.davinci.dto.fitme.exercise.ExerciseRequestDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
@Table(name = "exercise")
public class Exercise extends FitmeDomain<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "description")
    private String description;

    @Column(name = "difficulty")
    private String difficulty;

    public Exercise(ExerciseRequestDTO exercise) {
        this.name = exercise.getName();
        this.type = exercise.getType();
        this.description = exercise.getDescription();
        this.difficulty = exercise.getDifficulty();
    }

    public Exercise(Long id, ExerciseRequestDTO exercise) {
        this.id = id;
        this.name = exercise.getName();
        this.type = exercise.getType();
        this.description = exercise.getDescription();
        this.difficulty = exercise.getDifficulty();
    }
}