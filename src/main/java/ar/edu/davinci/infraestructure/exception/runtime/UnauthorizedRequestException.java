package ar.edu.davinci.infraestructure.exception.runtime;

import ar.edu.davinci.infraestructure.exception.FitmeException;

public class UnauthorizedRequestException extends FitmeException {

    public UnauthorizedRequestException(String message) {
        super(message);
    }

    public UnauthorizedRequestException(Throwable cause) {
        super(cause);
    }

    public UnauthorizedRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
