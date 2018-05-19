package infraestructure.security.util;

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

    private String email;

    private String nickname;
}
