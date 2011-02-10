package bad.robot.pingpong;

import com.google.code.tempusfugit.concurrency.ThreadUtils;

import java.util.Random;

import static com.google.code.tempusfugit.temporal.Duration.millis;

public class Introduce {

    private static final Random random = new Random();

    public static void jitter() {
        ThreadUtils.sleep(millis(random.nextInt(5)));
    }
}
