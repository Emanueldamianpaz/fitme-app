package ar.edu.davinci.infraestructure.security.authorization;

import ar.edu.davinci.infraestructure.security.authorization.enums.Exclusion;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public final class SecurityExclusions {

    public static boolean isUnrestricted(String path, String reqMethod) {
        Exclusion[] exclusionList = Exclusion.values();
        List<Boolean> pathUnrestricted = new ArrayList<>();

        log.debug("Checking if ['" + path + "'] is unrestricted.");

        for (Exclusion exclusion : exclusionList) {
            pathUnrestricted.add(exclusion.getExclusion().matcher(path).matches() && exclusion.getMethodsAvailable().contains(reqMethod));
        }
        return pathUnrestricted.contains(true);
    }
}
