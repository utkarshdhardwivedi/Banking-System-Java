package Custom_Exceptions;

public class DatabaseConnectionTimeoutException extends Exception {
    public DatabaseConnectionTimeoutException(String message) {
        super(message);
    }
}