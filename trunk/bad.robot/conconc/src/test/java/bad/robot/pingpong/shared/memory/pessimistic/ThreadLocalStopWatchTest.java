package bad.robot.pingpong.shared.memory.pessimistic;

import bad.robot.pingpong.StopWatch;
import com.google.code.tempusfugit.temporal.Clock;
import com.google.code.tempusfugit.temporal.Duration;
import org.junit.Test;

import java.util.Date;

import static com.google.code.tempusfugit.temporal.Duration.millis;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ThreadLocalStopWatchTest {

    private Date date = new Date();
    private final StubClock clock = new StubClock(date);
    private final StopWatch timer = new ThreadLocalStopWatch(clock);

    @Test
    public void startedButNotStopped() {
        timer.start();
        assertThat(timer.elapsedTime(), is(millis(0)));
    }

    @Test
    public void stop() {
        timer.start();
        advanceTimeBy(millis(5));
        timer.stop();
        assertThat(timer.elapsedTime(), is(millis(5)));
        advanceTimeBy(millis(5));
        timer.stop();
        assertThat(timer.elapsedTime(), is(millis(10l)));
    }

    private void advanceTimeBy(Duration duration) {
        long time = millis(date.getTime()).plus(duration).inMillis();
        date = new Date(time);
        clock.setDate(date);
    }

    private class StubClock implements Clock {
        private Date date;

        private StubClock(Date date) {
            this.date = date;
        }

        public Date create() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }

}
