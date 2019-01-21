package indianpoker.exception;

public class CannotEnterGameException extends RuntimeException {
    public CannotEnterGameException() {
    }

    public CannotEnterGameException(String message) {
        super(message);
    }
}
