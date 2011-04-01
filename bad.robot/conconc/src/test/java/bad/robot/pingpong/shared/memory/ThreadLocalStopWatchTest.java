package bad.robot.pingpong.shared.memory;

import bad.robot.pingpong.StopWatch;
import com.google.code.tempusfugit.temporal.MovableClock;
import org.junit.Test;

import static com.google.code.tempusfugit.temporal.Duration.millis;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ThreadLocalStopWatchTest {

    private final MovableClock clock = new MovableClock();
    private final StopWatch timer = new ThreadLocalStopWatch(clock);

    @Test
    public void startedButNotStopped() {
        timer.start();
        assertThat(timer.elapsedTime(), is(millis(0)));
    }

    @Test
    public void elapsedTime() {
        timer.start();
        clock.incrementBy(millis(5));
        timer.stop();
        assertThat(timer.elapsedTime(), is(millis(5)));
        clock.incrementBy(millis(5));
        timer.stop();
        assertThat(timer.elapsedTime(), is(millis(10)));
    }

}
