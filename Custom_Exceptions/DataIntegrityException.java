package Custom_Exceptions;

public class DataIntegrityException extends Exception {
    public DataIntegrityException(String message) {
        super(message);
    }
}