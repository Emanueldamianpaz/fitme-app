package ar.edu.davinci.infraestructure.security;

import com.auth0.AuthenticationController;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import com.github.racc.tscg.TypesafeConfig;
import ar.edu.davinci.exception.UnauthorizedRequestException;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.http.HttpMethod;
import spark.Filter;
import spark.Request;
import spark.Response;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

@Singleton
@Slf4j
public class SecurityFilter implements Filter {

    private Boolean enabled;
    public static JWTVerifier verifier;
    public static AuthenticationController authClient;
    private String fakeToken;
    private UserSessionFactory userSessionFactory;
    private String audience2;
    private String issuer;

    @Inject
    public SecurityFilter(
            RSAKeyProvider keyProvider,
            @TypesafeConfig("auth0.enabled") Boolean enabled,
            @TypesafeConfig("auth0.audience1") String audience1,
            @TypesafeConfig("auth0.audience2") String audience2,
            @TypesafeConfig("auth0.issuer") String issuer,
            @TypesafeConfig("auth0.clientId") String clientId,
            @TypesafeConfig("auth0.clientSecret") String clientSecret,
            @TypesafeConfig("auth0.fakeToken") String fakeToken,
            UserSessionFactory userSessionFactory
    ) {

        this.enabled = enabled;
        this.fakeToken = fakeToken;
        this.audience2 = audience2;
        this.issuer = issuer;
        this.userSessionFactory = userSessionFactory;
        this.authClient = AuthenticationController.newBuilder(issuer, clientId, clientSecret)
                .build();

        this.verifier = JWT.require(Algorithm.RSA256(keyProvider))
                .withIssuer(issuer)
                .withAudience(audience1, audience2)
                .build();
    }

    @Override
    public void handle(Request request, Response response) throws Exception {

        if (request.requestMethod().equals(HttpMethod.OPTIONS.asString()))
            return;


        if (!enabled) {

            UserSession userSession = userSessionFactory.createFakeUserSession(JWT.decode(fakeToken));
            request.attribute("current-session", userSession);

        } else {

            if (!SecurityExclusions.isUnrestricted(request.pathInfo(), request.requestMethod())) {
                getUserSession(request, response);
            } else {
                log.debug("Matches unrestricted access.");
            }
        }

    }

    public void getUserSession(Request request, Response response) {
        try {
            String authorizationHeader = request.headers("Authorization");

            if (!Optional.ofNullable(authorizationHeader).isPresent()) {
                Pattern pattern = PathAuthorization.UI_PATTERN.getPattern();
                Set<String> methodsAvailable = PathAuthorization.UI_PATTERN.getMethodsAvailable();
                String redirectUri = request.scheme() + "://" + request.host() + "/fitme/user/callback";

                if (pattern.matcher(request.pathInfo()).matches() && methodsAvailable.contains(request.requestMethod())) {
                    String authorizeUrl = authClient.
                            buildAuthorizeUrl(request.raw(), redirectUri)
                            .withAudience(String.format(audience2, issuer))
                            .build();
                    response.redirect(authorizeUrl);
                } else {
                    throw new UnauthorizedRequestException("Header Authorization needed!");
                }
            } else {
                DecodedJWT decodedJWT = verifier.verify(request.headers("Authorization"));
                userSessionFactory.createUserSession(decodedJWT);
            }
        } catch (JWTVerificationException e) {
            throw new UnauthorizedRequestException(e);
        }
//        return userSession;
    }
}
