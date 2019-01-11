package indianpoker.exception;

public class CanNotCallCaseException extends RuntimeException{
    public CanNotCallCaseException() {
    }

    public CanNotCallCaseException(String message) {
        super(message);
    }
}
