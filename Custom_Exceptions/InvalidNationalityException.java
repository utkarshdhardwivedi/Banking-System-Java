package Custom_Exceptions;

public class InvalidNationalityException extends Exception {
    public InvalidNationalityException(String message) {
        super(message);
    }
}