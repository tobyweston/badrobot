package bad.robot.pingpong.shared.memory;

import bad.robot.pingpong.Introduce;
import bad.robot.pingpong.StopWatch;
import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicLong;

import static com.google.code.tempusfugit.temporal.Duration.millis;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ThreadLocalStopWatchIntegrationTest {

    private final static ThreadLocalMovableClock clock = new ThreadLocalMovableClock();
    private final static StopWatch timer = new ThreadLocalStopWatch(clock);

    private final static AtomicLong totalTime = new AtomicLong(0L);

    @Rule public RepeatingRule repeating = new RepeatingRule();
    @Rule public ConcurrentRule concurrent = new ConcurrentRule();

    @Test
    @Concurrent(count = 10)
    @Repeating(repetition = 50)
    public void elapsedTimeWhenUsingThreadLocalMovableClock() throws InterruptedException {
        clock.incrementBy(millis(100));
        timer.stop();
        totalTime.getAndSet(timer.elapsedTime().inMillis());
        Introduce.jitter();
    }

    @AfterClass
    public static void verify() {
        long expected = 100 * 50 * 10;
        assertThat(totalTime.get(), is(expected));
    }
}
