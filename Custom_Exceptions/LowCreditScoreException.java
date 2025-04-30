package Custom_Exceptions;

public class LowCreditScoreException extends Exception {
    public LowCreditScoreException(String message) {
        super(message);
    }
}