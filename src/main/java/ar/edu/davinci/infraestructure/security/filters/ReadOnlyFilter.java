package ar.edu.davinci.infraestructure.security.filters;

import ar.edu.davinci.exception.runtime.UnauthorizedRequestException;
import ar.edu.davinci.infraestructure.security.roles.FitmeRoles;
import ar.edu.davinci.infraestructure.security.session.UserSession;
import org.eclipse.jetty.http.HttpMethod;
import spark.Filter;
import spark.Request;
import spark.Response;

import javax.inject.Singleton;

@Singleton
public class ReadOnlyFilter implements Filter {
    @Override
    public void handle(Request request, Response response) throws Exception {

        if (request.requestMethod().equals(HttpMethod.OPTIONS.asString()))
            return;

        UserSession session = request.attribute("current-session");

        if (session.getRole().equals(FitmeRoles.READONLY) && !request.requestMethod().equals(HttpMethod.GET.asString()))
            throw new UnauthorizedRequestException("You are only authorized to read resources.");
    }
}
