package bad.robot.pingpong.shared.memory;

import bad.robot.pingpong.StopWatch;
import bad.robot.pingpong.shared.memory.pessimistic.AtomicLongCounter;
import bad.robot.pingpong.shared.memory.pessimistic.AtomicMillisecondCounter;
import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;

import static com.google.code.tempusfugit.temporal.Duration.millis;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ThroughputIntegrationTest {

    private static final ThreadLocalMovableClock clock = new ThreadLocalMovableClock();
    private static final StopWatch timer = new ThreadLocalStopWatch(clock);
    private static final Throughput throughput = new Throughput(timer, new AtomicLongCounter(), new AtomicMillisecondCounter());

    @Rule public ConcurrentRule concurrent = new ConcurrentRule();
    @Rule public RepeatingRule repeating = new RepeatingRule();

    @Concurrent (count = 10)
    @Repeating (repetition = 100)
    @Test
    public void recordThroughput() {
        RequestObserver.Request request = throughput.started();
        clock.incrementBy(millis(250));
        request.finished();
    }
    
    @AfterClass
    public static void verify() {
        assertThat(throughput.getTotalRequests(), is(1000L));
        assertThat(throughput.getRequestsPerSecond(), is(4.0D));
    }
}
