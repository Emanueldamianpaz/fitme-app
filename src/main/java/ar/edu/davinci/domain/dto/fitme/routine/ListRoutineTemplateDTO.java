package ar.edu.davinci.domain.dto.fitme.routine;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ListRoutineTemplateDTO {

    private List<Long> routine_template_ids;
}
