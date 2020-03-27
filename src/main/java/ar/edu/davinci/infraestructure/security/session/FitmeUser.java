package ar.edu.davinci.infraestructure.security.session;

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
    private String genre;
    private String email;
    private String nickname;
}
