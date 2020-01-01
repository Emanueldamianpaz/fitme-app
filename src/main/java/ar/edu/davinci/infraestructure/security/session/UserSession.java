package ar.edu.davinci.infraestructure.security.session;

import ar.edu.davinci.exception.FitmeException;
import ar.edu.davinci.infraestructure.security.roles.FitmeRoles;
import ar.edu.davinci.infraestructure.security.util.FitmeUser;
import com.auth0.jwt.interfaces.Payload;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Optional;

@NoArgsConstructor
@Data
public class UserSession {

    private Payload payload;

    private FitmeUser user;

    public UserSession(Payload payload) {

        this.payload = payload;

        this.user = FitmeUser.builder()
                .id(payload.getSubject())
                .name(payload.getClaim("given_name").asString())
                .last_name(payload.getClaim("family_name").asString())
                .picture(payload.getClaim("picture").asString())
                .genre(payload.getClaim("genre").asString())
                .nickname(payload.getClaim("nickname").asString())
                .email(payload.getClaim("email").asString())
                .build();
    }

    public Optional<HashMap<String, Object>> getAppMetadata() {
        return Optional.ofNullable(this.payload.getClaim("http://localhost:4567/fitme/api/app_metadata").as(HashMap.class));
    }

    public Optional<FitmeRoles> getUncheckedRole() {

        return this.getAppMetadata()
                .map(metadata -> metadata.getOrDefault("role", null))
                .map(value -> FitmeRoles.valueOf((String) value));
    }

    public FitmeRoles getRole() {
        return this.getUncheckedRole().orElseThrow(() -> new FitmeException("Must be a role assigned to user."));
    }
}
