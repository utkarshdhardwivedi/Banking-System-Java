package Custom_Exceptions;

public class AgeRestrictionException extends Exception {
    public AgeRestrictionException(String message) {
        super(message);
    }
}