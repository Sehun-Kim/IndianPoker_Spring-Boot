package indianpoker.exception;

public class CanNotCallCaseException extends RuntimeException{
    private String playerName;

    public CanNotCallCaseException() {
    }

    public CanNotCallCaseException(String message) {
        super(message);
    }

    public CanNotCallCaseException(String message, String playerName) {
        super(message);
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }
}
