package ar.edu.davinci.dto.fitme.routine;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class SetRoutineRequestDTO {

    private List<Long> routines;
}
