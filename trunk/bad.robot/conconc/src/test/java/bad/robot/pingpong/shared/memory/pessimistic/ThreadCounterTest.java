package bad.robot.pingpong.shared.memory.pessimistic;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ThreadCounterTest {

    private final ThreadCounter counter = new ThreadCounter();

    @Test
    public void shouldInitialiseCounts() {
        assertThat(counter.getActiveCount(), is(0L));
        assertThat(counter.getCreatedCount(), is(0L));
    }

    @Test
    public void shouldIncrementActiveCount() {
        incrementActiveThreadsBy(3);
        assertThat(counter.getActiveCount(), is(3L));
    }

    @Test
    public void shouldDecrementActiveThreadCount() {
        incrementActiveThreadsBy(5);
        assertThat(counter.getActiveCount(), is(5L));
        decrementActiveThreadsBy(5);
        assertThat(counter.getActiveCount(), is(0L));
    }

    @Test
    public void shouldResetCounts() {
        incrementActiveThreadsBy(8);
        incrementThreadsBy(5);
        counter.reset();
        assertThat(counter.getActiveCount(), is(0L));
        assertThat(counter.getCreatedCount(), is(0L));
    }

    private void incrementActiveThreadsBy(int by) {
        for (int i = 0; i < by; i++)
            counter.threadStarted();
    }

    private void decrementActiveThreadsBy(int by)  {
        for (int i = 0; i < by; i++)
            counter.threadTerminated();
    }

    private void incrementThreadsBy(int by)  {
        for (int i = 0; i < by; i++)
            counter.threadCreated();
    }

}
