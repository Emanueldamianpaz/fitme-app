package ar.edu.davinci.dto.fitme.routine;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RoutineLightResponseDTO {

    private Long id;
    private String name;
    private String description;
}
