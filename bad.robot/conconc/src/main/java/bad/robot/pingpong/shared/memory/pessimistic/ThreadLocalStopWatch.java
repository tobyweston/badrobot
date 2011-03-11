package bad.robot.pingpong.shared.memory.pessimistic;

import bad.robot.pingpong.StopWatch;
import com.google.code.tempusfugit.temporal.Clock;
import com.google.code.tempusfugit.temporal.Duration;

import java.util.Date;

import static com.google.code.tempusfugit.temporal.Duration.millis;

public class ThreadLocalStopWatch implements StopWatch {

    private final Clock clock;

    private final ThreadLocal<Long> started;
    private final ThreadLocal<Long> stopped;

    public ThreadLocalStopWatch(Clock clock) {
        this.clock = clock;
        Date now = this.clock.create();
        started = new LongThreadLocal(now.getTime());
        stopped = new LongThreadLocal(now.getTime());
    }

    @Override
    public void start() {
        started.set(clock.create().getTime());
    }

    @Override
    public void stop() {
        stopped.set(clock.create().getTime());
    }

    @Override
    public Duration elapsedTime() {
        return millis(stopped.get() - started.get());
    }

    private static class LongThreadLocal extends ThreadLocal<Long> {
        private final long initialValue;

        public LongThreadLocal(long initialValue) {
            this.initialValue = initialValue;
        }

        @Override
        protected Long initialValue() {
            return Long.valueOf(initialValue);
        }
    }
}
