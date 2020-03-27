package ar.edu.davinci.infraestructure.exception.runtime;

import ar.edu.davinci.infraestructure.exception.FitmeException;

public class MissingParameterException extends FitmeException {

    public MissingParameterException(String parameter) {
        super(String.format("Missing parameter '%s'", parameter));
    }
}
