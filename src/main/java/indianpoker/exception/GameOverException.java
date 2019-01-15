package indianpoker.exception;

public class GameOverException extends RuntimeException {
    public GameOverException() {
    }

    public GameOverException(String message) {
        super(message);
    }
}
