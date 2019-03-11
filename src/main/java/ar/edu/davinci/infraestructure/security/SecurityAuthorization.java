package ar.edu.davinci.infraestructure.security;


import com.auth0.jwt.JWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
public final class SecurityAuthorization {


    public static boolean isAuthorized(String path, JWT jwt, String method) {

        Boolean authorized;

        PathAuthorization match = Arrays.stream(PathAuthorization.values())
                .filter(pathSecure -> pathSecure.getPattern().matcher(path).matches() && pathSecure.getMethodsAvailable().contains(method))
                .collect(Collectors.toList()).get(0);

        log.debug("Checking if " + jwt + " it's authorized to ['" + path + "'] .");

        List<Role> rolesMatch = match.getRoles();

        if (rolesMatch.contains(Role.NO_AUTHORIZATION)) {
            authorized = true;
        } else {
            List<String> allowedPermission = rolesMatch.stream()
                    .map(role -> role.getPermission()).collect(Collectors.toList())
                    .stream().flatMap(Collection::stream)
                    .distinct()
                    .collect(Collectors.toList());

            authorized = allowedPermission.stream()
                    //.map(permission -> jwt.hasPermission(permission))
                    .collect(Collectors.toList())
                    .contains(true);
        }
        return authorized;
    }

}