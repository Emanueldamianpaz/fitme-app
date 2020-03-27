package ar.edu.davinci.infraestructure.exception.runtime;

import ar.edu.davinci.infraestructure.exception.FitmeException;

public class BusinessConstrainException extends FitmeException {

    public BusinessConstrainException(String message) {
        super(message);
    }
}
