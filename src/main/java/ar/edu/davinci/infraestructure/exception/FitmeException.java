package ar.edu.davinci.infraestructure.exception;

public class FitmeException extends RuntimeException {

    public FitmeException(String message) {
        super(message);
    }

    public FitmeException(Throwable cause) {
        super(cause);
    }

    public FitmeException(String message, Throwable cause) {
        super(message, cause);
    }

}
