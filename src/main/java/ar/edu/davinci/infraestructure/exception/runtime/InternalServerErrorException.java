package ar.edu.davinci.infraestructure.exception.runtime;

import ar.edu.davinci.infraestructure.exception.FitmeException;

public class InternalServerErrorException extends FitmeException {

    public InternalServerErrorException(Throwable cause) {
        super(cause);
    }
}
