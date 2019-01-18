package indianpoker.exception;

public class CannotRaiseException extends RuntimeException {
    private String playerName;

    public CannotRaiseException() {
    }

    public CannotRaiseException(String message) {
        super(message);
    }

    public CannotRaiseException(String message, String playerName) {
        super(message);
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }
}
