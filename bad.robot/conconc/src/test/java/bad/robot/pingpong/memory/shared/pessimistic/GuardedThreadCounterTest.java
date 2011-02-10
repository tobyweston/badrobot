package bad.robot.pingpong.memory.shared.pessimistic;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GuardedThreadCounterTest {

    private final GuardedThreadCounter counter = new GuardedThreadCounter();

    @Test
    public void shouldInitialiseThreadCounts() {
        assertThat(counter.getActiveThreads(), is(0L));
    }

    @Test
    public void shouldIncrementActiveThreadCount() {
        incrementActiveThreads(by(3));
        assertThat(counter.getActiveThreads(), is(3L));
    }

    @Test
    public void shouldIncrementThreadCount() {
        incrementThreads(by(4));
        assertThat(counter.getThreadCount(), is(4L));
    }

    @Test
    public void shouldDecrementActiveThreadCount() {
        incrementActiveThreads(by(5));
        assertThat(counter.getActiveThreads(), is(5L));
        decrementActiveThreads(by(5));
        assertThat(counter.getActiveThreads(), is(0L));
    }

    @Test
    public void shouldResetCounts() {
        incrementActiveThreads(by(8));
        incrementThreads(by(5));
        counter.reset();
        assertThat(counter.getActiveThreads(), is(0L));
        assertThat(counter.getThreadCount(), is(0L));
    }

    private void incrementActiveThreads(int by) {
        for (int i = 0; i < by; i++)
            counter.incrementActiveThreads();
    }

    private void decrementActiveThreads(int by)  {
        for (int i = 0; i < by; i++)
            counter.decrementActiveThreads();
    }

    private void incrementThreads(int by)  {
        for (int i = 0; i < by; i++)
            counter.incrementThreadCount();
    }

    private static int by(int count) {
        return count;
    }

}
