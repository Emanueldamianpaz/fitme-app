package ar.edu.davinci.infraestructure.security;

import com.google.common.collect.ImmutableSet;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public enum PathAuthorization {
    ADMIN_PATTERN(Pattern.compile("/admin.*"), ImmutableSet.of("GET", "POST", "PUT", "DELETE"), Arrays.asList(Role.ADMIN)),
    APP_PATTERN(Pattern.compile("/app.*"), ImmutableSet.of("GET"), Arrays.asList(Role.NO_AUTHORIZATION)),
    EXPORT_CSV_PATTERN(Pattern.compile("/export\\.csv.*"), ImmutableSet.of("GET"), Arrays.asList(Role.NO_AUTHORIZATION)),

    SERVICE_PATTERN(Pattern.compile("/service.*"), ImmutableSet.of("POST", "PUT", "DELETE"), Arrays.asList(Role.NO_AUTHORIZATION));

    private Pattern pattern;
    private Set<String> methodsAvailable;
    private List<Role> roles;

    PathAuthorization(Pattern pattern, Set<String> methodsAvailable, List<Role> roles) {
        this.pattern = pattern;
        this.methodsAvailable = methodsAvailable;
        this.roles = roles;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public Set<String> getMethodsAvailable() {
        return methodsAvailable;
    }
}