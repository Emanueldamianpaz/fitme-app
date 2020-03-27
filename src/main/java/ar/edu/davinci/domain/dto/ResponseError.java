package ar.edu.davinci.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseError {

    private String code;
    private String description;
}
