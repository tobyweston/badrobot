package bad.robot.pingpong.memory.shared.pessimistic;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GuardedThreadStatisticsTest {

    private final GuardedThreadStatistics statistics = new GuardedThreadStatistics();

    @Test
    public void shouldInitialiseThreadCounts() {
        assertThat(statistics.getActiveThreads(), is(0L));
    }

    @Test
    public void shouldIncrementActiveThreadCount() {
        incrementActiveThreads(by(3));
        assertThat(statistics.getActiveThreads(), is(3L));
    }

    @Test
    public void shouldIncrementThreadCount() {
        incrementThreads(by(4));
        assertThat(statistics.getThreadCount(), is(4L));
    }

    @Test
    public void shouldDecrementActiveThreadCount() {
        incrementActiveThreads(by(5));
        assertThat(statistics.getActiveThreads(), is(5L));
        decrementActiveThreads(by(5));
        assertThat(statistics.getActiveThreads(), is(0L));
    }

    @Test
    public void shouldResetCounts() {
        incrementActiveThreads(by(8));
        incrementThreads(by(5));
        statistics.reset();
        assertThat(statistics.getActiveThreads(), is(0L));
        assertThat(statistics.getThreadCount(), is(0L));
    }

    private void incrementActiveThreads(int by) {
        for (int i = 0; i < by; i++)
            statistics.incrementActiveThreads();
    }

    private void decrementActiveThreads(int by)  {
        for (int i = 0; i < by; i++)
            statistics.decrementActiveThreads();
    }

    private void incrementThreads(int by)  {
        for (int i = 0; i < by; i++)
            statistics.incrementThreadCount();
    }

    private static int by(int count) {
        return count;
    }

}
