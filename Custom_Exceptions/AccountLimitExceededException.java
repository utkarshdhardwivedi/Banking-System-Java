package Custom_Exceptions;

public class AccountLimitExceededException extends Exception {
    public AccountLimitExceededException(String message) {
        super(message);
    }
}