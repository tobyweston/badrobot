package bad.robot.pingpong.shared.memory;

import bad.robot.pingpong.StopWatch;
import bad.robot.pingpong.shared.memory.pessimistic.AtomicMillisecondCounter;
import com.google.code.tempusfugit.temporal.Duration;

public class Throughput implements RequestObserver, ThroughputMBean {

    private final StopWatch timer;
    private final Counter count = new LongCounter();
    private final AccumulatingCounter<Duration> totalTime = new AtomicMillisecondCounter();

    public Throughput(StopWatch timer) {
        this.timer = timer;
    }

    @Override
    public Request started() {
        timer.start();
        return new Request() {
            @Override
            public Duration finished() {
                count.increment();
                timer.stop();
                totalTime.add(timer.elapsedTime());
                return timer.elapsedTime();
            }
        };
    }

    @Override
    public Double getRequestsPerSecond() {
        return (double) count.get() / ((double) totalTime.get() / 1000);
    }

    @Override
    public Long getTotalRequests() {
        return count.get();
    }
}
