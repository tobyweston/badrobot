package bad.robot.pingpong.shared.memory;

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
public abstract class AbstractThreadCounterIntegrationTest {

    protected static ThreadCounter counter;

    @Rule public ConcurrentRule concurrent = new ConcurrentRule();
    @Rule public RepeatingRule repeating = new RepeatingRule();

    @Test
    @Repeating
    @Concurrent(count = 50)
    public void notifyThreadStarted() {
        counter.threadStarted();
        Introduce.jitter();
    }

    @Test
    @Repeating
    @Concurrent(count = 10)
    public void notifyThreadTerminated() {
        counter.threadTerminated();
        Introduce.jitter();
    }

    @Test
    @Repeating
    @Concurrent(count = 50)
    public void notifyThreadCreated() {
        counter.threadCreated();
        Introduce.jitter();
    }

    @AfterClass
    public static void verifyCounter() {
        assertThat(counter.getActiveThreads(), is(4000L));
        assertThat(counter.getCreatedThreads(), is(5000L));
    }
}
