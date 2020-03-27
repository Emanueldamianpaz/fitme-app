package ar.edu.davinci.infraestructure.exception.runtime;

import ar.edu.davinci.infraestructure.exception.FitmeException;

public class ResourceNotFoundException extends FitmeException {

    public ResourceNotFoundException(String resource) {
        super("Not found " + resource);
    }
}
