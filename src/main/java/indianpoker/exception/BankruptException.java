package indianpoker.exception;

public class BankruptException extends RuntimeException {
    public BankruptException() {
    }

    public BankruptException(String message) {
        super(message);
    }
}
