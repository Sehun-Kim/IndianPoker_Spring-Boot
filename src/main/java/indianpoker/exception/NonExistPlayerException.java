package indianpoker.exception;

public class NonExistPlayerException extends RuntimeException {

    public NonExistPlayerException() {
    }

    public NonExistPlayerException(String message) {
        super(message);
    }
}
