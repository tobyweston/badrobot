package bad.robot.pingpong.shared.memory.pessimistic;

import bad.robot.pingpong.Introduce;
import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.ConcurrentTestRunner;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(ConcurrentTestRunner.class)
public class GuardedThreadCounterIntegrationTest {

    private static final GuardedThreadCounter counter = new GuardedThreadCounter();

    @Rule public ConcurrentRule concurrent = new ConcurrentRule();
    @Rule public RepeatingRule repeating = new RepeatingRule();

    @Test
    @Repeating
    @Concurrent(count = 50)
    public void incrementActiveThreadCounters() {
        counter.incrementActiveThreads();
        Introduce.jitter();
    }

    @Test
    @Repeating
    @Concurrent(count = 10)
    public void decrementActiveThreadCounters() {
        counter.decrementActiveThreads();
        Introduce.jitter();
    }

    @Test
    @Repeating
    @Concurrent(count = 50)
    public void incrementThreadCount() {
        counter.incrementCreatedThreads();
        Introduce.jitter();
    }

    @AfterClass
    public static void verifyCounter() {
        assertThat(counter.getActiveThreads(), is(4000L));
        assertThat(counter.getThreadCount(), is(5000L));
    }

}
