package bad.robot.turtle;

public class Defect extends RuntimeException {
    public Defect() {
    }

    public Defect(String message) {
        super(message);
    }

    public Defect(String message, Throwable cause) {
        super(message, cause);
    }

    public Defect(Throwable cause) {
        super(cause);
    }
}
