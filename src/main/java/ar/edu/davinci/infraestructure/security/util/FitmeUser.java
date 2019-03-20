package ar.edu.davinci.infraestructure.security.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FitmeUser {

    private String id;
    private String name;
    private String last_name;
    private String picture;
    private String gender;
    private String email;
    private String nickname;
}
