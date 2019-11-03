package ar.edu.davinci.exception.runtime;

import ar.edu.davinci.exception.FitmeException;

public class InternalServerErrorException extends FitmeException {

    public InternalServerErrorException(Throwable cause) {
        super(cause);
    }
}
