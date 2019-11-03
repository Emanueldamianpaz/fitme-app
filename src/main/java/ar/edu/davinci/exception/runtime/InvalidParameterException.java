package ar.edu.davinci.exception.runtime;

import ar.edu.davinci.exception.FitmeException;

public class InvalidParameterException extends FitmeException {

    public InvalidParameterException(String message) {
        super(message);
    }

    public InvalidParameterException(String message, Throwable cause) {
        super(message, cause);
    }
}
