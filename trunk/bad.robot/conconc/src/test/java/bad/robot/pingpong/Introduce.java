package bad.robot.pingpong;

import static bad.robot.pingpong.UpTo.upTo;
import static com.google.code.tempusfugit.concurrency.ThreadUtils.sleep;
import static com.google.code.tempusfugit.temporal.Duration.millis;

public class Introduce {

    public static void jitter() {
        jitter(upTo(millis(5)));
    }

    public static void jitter(UpTo upTo) {
        sleep(upTo.duration());
    }
}
