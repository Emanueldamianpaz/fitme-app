package ar.edu.davinci.dto.fitme.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
@Builder
public class UserSessionDTO {

    private String token_id;
    private String role;
}
