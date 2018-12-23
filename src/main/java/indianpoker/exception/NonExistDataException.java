package indianpoker.exception;

public class NonExistDataException extends RuntimeException {

    public NonExistDataException() {
    }

    public NonExistDataException(String message) {
        super(message);
    }
}
