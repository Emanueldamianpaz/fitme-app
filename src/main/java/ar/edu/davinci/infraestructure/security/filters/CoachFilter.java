package ar.edu.davinci.infraestructure.security.filters;

import ar.edu.davinci.domain.FitmeRoles;
import ar.edu.davinci.infraestructure.exception.runtime.UnauthorizedRequestException;
import ar.edu.davinci.infraestructure.security.session.UserSession;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.eclipse.jetty.http.HttpMethod;
import spark.Filter;
import spark.Request;
import spark.Response;

import javax.inject.Singleton;

@Singleton
public class CoachFilter implements Filter {
    @Override
    public void handle(Request request, Response response) throws Exception {

        if (request.requestMethod().equals(HttpMethod.OPTIONS.asString()))
            return;

        DecodedJWT jwt = JWT.decode(request.headers("Authorization"));
        UserSession session = new UserSession(jwt);

        if (session.getRole().equals(FitmeRoles.COACH) && !request.requestMethod().equals(HttpMethod.GET.asString()))
            throw new UnauthorizedRequestException("You are only authorized to read resources.");
    }
}
