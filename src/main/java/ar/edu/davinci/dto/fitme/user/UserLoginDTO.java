package ar.edu.davinci.dto.fitme.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserLoginDTO {
    private String usernameOrEmail;
    private String password;
}
