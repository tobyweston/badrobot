package bad.robot.pingpong;

import com.google.code.tempusfugit.temporal.Duration;

import java.util.Random;

import static com.google.code.tempusfugit.temporal.Duration.millis;

public class UpTo {

    private final Duration duration;
    private final Random random = new Random();

    private UpTo(Duration duration) {
        this.duration = duration;
    }

    public static UpTo upTo(Duration duration) {
        return new UpTo(duration);
    }

    public Duration duration() {
        return millis(random.nextInt((int) duration.inMillis()));
    }
}
