package ar.edu.davinci.infraestructure.security.authorization.enums;

import com.google.common.collect.ImmutableSet;

import java.util.Set;
import java.util.regex.Pattern;

public enum Exclusion {


    APP_METADATA_PATH(Pattern.compile("/fitme/api/app_metadata"), ImmutableSet.of("GET")),

    INDEX_PATH(Pattern.compile("/fitme/ui/index.html"), ImmutableSet.of("GET")),

    STATIC_PATTERN(Pattern.compile("/fitme/ui/css.*"), ImmutableSet.of("GET")),
    RESOURCES_PATTERN(Pattern.compile("/fitme/ui/js.*"), ImmutableSet.of("GET")),
    WEBJARS_PATTERN(Pattern.compile("/fitme/ui/webjars.*"), ImmutableSet.of("GET")),
    VIEW_PATTERN(Pattern.compile("/fitme/ui/views.*"), ImmutableSet.of("GET")),
    IMG_PATTERN(Pattern.compile("/fitme/ui/img.*"), ImmutableSet.of("GET")),
    I18N_PATTERN(Pattern.compile("/fitme/ui/i18n.*"), ImmutableSet.of("GET")),

    CALLBACK_PATTERN(Pattern.compile("/fitme/user/callback.*"), ImmutableSet.of("GET")),

    VERSION(Pattern.compile("/fitme/version"), ImmutableSet.of("GET")),
    HEALTH_CHECK_PATTERN(Pattern.compile("/fitme/health-check"), ImmutableSet.of("GET"));

    private Pattern exclusion;
    private Set<String> methodsAvailable;

    Exclusion(Pattern exclusion, Set<String> methodsAvailable) {
        this.exclusion = exclusion;
        this.methodsAvailable = methodsAvailable;
    }

    public Pattern getExclusion() {
        return exclusion;
    }

    public Set<String> getMethodsAvailable() {
        return methodsAvailable;
    }
}
