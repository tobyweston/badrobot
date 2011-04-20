package bad.robot.pingpong.shared.memory;

import com.google.code.tempusfugit.temporal.Duration;
import org.junit.Test;

import static com.google.code.tempusfugit.temporal.Duration.millis;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ThroughputTest {

    private final StopWatchStub timer = new StopWatchStub();
    private final Throughput throughput = new Throughput(timer);

    @Test
    public void calculateThroughput() throws Exception {
        makeRequestLasting(millis(250));
        makeRequestLasting(millis(150));
        makeRequestLasting(millis(50));
        makeRequestLasting(millis(300));
        assertThat(throughput.getRequestsPerSecond(), is(5.333333333333333));
    }

    private void makeRequestLasting(Duration duration) {
        RequestObserver.Request request = throughput.started();
        timer.setElapsedTime(duration);
        request.finished();
    }
}
