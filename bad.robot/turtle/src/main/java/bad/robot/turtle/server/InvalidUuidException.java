package bad.robot.turtle.server;

public class InvalidUuidException extends RuntimeException {

    public InvalidUuidException() {
    }

    public InvalidUuidException(String message) {
        super(message);
    }

    public InvalidUuidException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUuidException(Throwable cause) {
        super(cause);
    }
}
