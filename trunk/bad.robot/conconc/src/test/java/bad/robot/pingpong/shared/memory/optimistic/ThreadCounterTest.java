package bad.robot.pingpong.shared.memory.optimistic;

import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.TimeoutException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ThreadCounterTest {

    private final ThreadCounter counter = new ThreadCounter();

    @Test
    public void shouldIncrementActiveCount() {
        assertThat(counter.getActiveThreads(), is(0L));
        counter.threadStarted();
        assertThat(counter.getActiveThreads(), is(1L));
    }

    @Test
    public void shouldDecrementActiveThreadCount() {
        assertThat(counter.getActiveThreads(), is(0L));
        counter.threadStarted();
        counter.threadTerminated();
        assertThat(counter.getActiveThreads(), is(0L));
    }

    @Test
    public void shouldIncrementCreatedCount() throws TimeoutException, InterruptedException {
        assertThat(counter.getCreatedThreads(), is(0L));
        counter.threadCreated();
        assertThat(counter.getCreatedThreads(), is(1L));
    }

    @Test
    @Ignore
    public void shouldResetCounts() {
//        counter.reset();
    }
}
