package ar.edu.davinci.exception.runtime;

import ar.edu.davinci.exception.FitmeException;

public class MissingParameterException extends FitmeException {

    public MissingParameterException(String parameter) {
        super(String.format("Missing parameter '%s'", parameter));
    }
}
