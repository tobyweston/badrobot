package bad.robot.pingpong.shared.memory;

import bad.robot.pingpong.ClassInvariantViolation;
import bad.robot.pingpong.StopWatch;
import org.junit.Test;

import java.util.Date;

import static com.google.code.tempusfugit.temporal.Duration.millis;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ThreadLocalStopWatchTest {

    private final MovableClock clock = new MovableClock(new Date(10000));
    private final StopWatch timer = new ThreadLocalStopWatch(clock);

    @Test
    public void startedButNotStopped() {
        timer.start();
        assertThat(timer.elapsedTime(), is(millis(0)));
    }

    @Test
    public void shouldRecordElapsedTimeBetweenStartAndStop() {
        timer.start();
        clock.incrementBy(millis(5));
        timer.stop();
        assertThat(timer.elapsedTime(), is(millis(5)));
        clock.incrementBy(millis(5));
        timer.stop();
        assertThat(timer.elapsedTime(), is(millis(10)));
        timer.start();
        clock.incrementBy(millis(20));
        timer.stop();
        assertThat(timer.elapsedTime(), is(millis(20)));
    }

    @Test
    public void stoppedButNotStarted() {
        timer.stop();
        assertThat(timer.elapsedTime(), is(millis(0)));
    }

    @Test (expected = ClassInvariantViolation.class)
    public void stoppedThenStarted() {
        timer.stop();
        clock.incrementBy(millis(100));
        timer.start();
        assertThat(timer.elapsedTime(), is(millis(-100)));
    }
}
