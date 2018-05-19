package exception;

public class MissingParameterException extends FitmeException {

    public MissingParameterException(String parameter) {
        super(String.format("Missing parameter '%s'", parameter));
    }
}
