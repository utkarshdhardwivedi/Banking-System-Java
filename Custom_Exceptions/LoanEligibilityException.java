package Custom_Exceptions;

public class LoanEligibilityException extends Exception {
    public LoanEligibilityException(String message) {
        super(message);
    }
}