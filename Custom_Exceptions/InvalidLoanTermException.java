package Custom_Exceptions;

public class InvalidLoanTermException extends Exception {
    public InvalidLoanTermException(String message) {
        super(message);
    }
}