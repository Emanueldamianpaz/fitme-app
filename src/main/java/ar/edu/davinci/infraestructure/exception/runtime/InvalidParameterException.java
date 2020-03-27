package ar.edu.davinci.infraestructure.exception.runtime;

import ar.edu.davinci.infraestructure.exception.FitmeException;

public class InvalidParameterException extends FitmeException {

    public InvalidParameterException(String message) {
        super(message);
    }

    public InvalidParameterException(String message, Throwable cause) {
        super(message, cause);
    }
}
