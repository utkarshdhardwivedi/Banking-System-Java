package Custom_Exceptions;

public class DuplicatePhoneNumberException extends Exception {
    public DuplicatePhoneNumberException(String message) {
        super(message);
    }
}