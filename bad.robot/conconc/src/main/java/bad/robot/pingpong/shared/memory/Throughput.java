package bad.robot.pingpong.shared.memory;

import bad.robot.pingpong.StopWatch;
import com.google.code.tempusfugit.temporal.Duration;

public class Throughput implements RequestObserver, ThroughputMBean {

    private final StopWatch timer;
    private final Counter count;
    private final AccumulatingCounter<Duration> totalTime;

    public Throughput(StopWatch timer, Counter count, AccumulatingCounter<Duration> totalTime) {
        this.timer = timer;
        this.count = count;
        this.totalTime = totalTime;
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
