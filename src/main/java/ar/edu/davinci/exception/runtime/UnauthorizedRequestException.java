package ar.edu.davinci.exception.runtime;

import ar.edu.davinci.exception.FitmeException;

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
