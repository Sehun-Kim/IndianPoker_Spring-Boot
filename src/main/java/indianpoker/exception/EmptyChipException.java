package indianpoker.exception;

public class EmptyChipException extends RuntimeException {
    public EmptyChipException() {
    }

    public EmptyChipException(String message) {
        super(message);
    }
}
