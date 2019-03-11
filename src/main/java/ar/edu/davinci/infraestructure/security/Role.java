package ar.edu.davinci.infraestructure.security;

import java.util.Arrays;
import java.util.List;

public enum Role {
    ADMIN(Arrays.asList("admin")),
    NO_AUTHORIZATION(Arrays.asList("no_authorization"));

    private List<String> permission;

    Role(List<String> permission) {
        this.permission = permission;
    }

    public List<String> getPermission() {
        return permission;
    }

}