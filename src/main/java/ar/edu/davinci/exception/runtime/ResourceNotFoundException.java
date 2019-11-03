package ar.edu.davinci.exception.runtime;

import ar.edu.davinci.exception.FitmeException;

public class ResourceNotFoundException extends FitmeException {

    public ResourceNotFoundException(String resource) {
        super("Not found " + resource);
    }
}
