package ar.edu.davinci.domain.dto.fitme.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserInfoLightRequestDTO {

    private String id;
    private UserInfoRequestDTO userInfo;
    private String name;
    private String lastName;
    private String email;
    private String picture;
    private String nickname;
    private String genre;
    private String role;
}
