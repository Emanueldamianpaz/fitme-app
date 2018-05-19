package exception;

public class ResourceNotFoundException extends FitmeException {

    public ResourceNotFoundException(String resource) {
        super("Not found " + resource);
    }
}
