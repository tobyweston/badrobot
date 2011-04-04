package bad.robot.pingpong.shared.memory;

import bad.robot.pingpong.ClassInvariantViolation;
import bad.robot.pingpong.StopWatch;
import com.google.code.tempusfugit.temporal.Duration;

import java.util.Date;

import static com.google.code.tempusfugit.temporal.Duration.millis;

/**
 * The {@link ThreadLocal} based stop watch is safe to use with multiple threads as it will time each thread's call
 * between {@link #start()} and {@link #stop()}. It is intended to use one shared {@link Clock} in order to base it's
 * timings on. For example, {@link RealClock} but you could use a clock based on {@link ThreadLocal} if you need to
 * provide different clocks per thread (although, outside of a testing context, I'm not sure why you'd want to).
 */
public class ThreadLocalStopWatch implements StopWatch {

    private final Clock clock;

    private final ThreadLocal<Long> started;
    private final ThreadLocal<Long> stopped;

    public ThreadLocalStopWatch(Clock clock) {
        this.clock = clock;
        Date now = this.clock.time();
        started = new ThreadLocalLong(now.getTime());
        stopped = new ThreadLocalLong(now.getTime());
    }

    @Override
    public void start() {
        started.set(clock.time().getTime());
    }

    @Override
    public void stop() {
        stopped.set(clock.time().getTime());
    }

    @Override
    public Duration elapsedTime() {
        if (stopped.get() < started.get())
            throw new ClassInvariantViolation("please start the stop watch before stopping it");
        return millis(stopped.get() - started.get());
    }

    private static class ThreadLocalLong extends ThreadLocal<Long> {
        private final long initialValue;

        public ThreadLocalLong(long initialValue) {
            this.initialValue = initialValue;
        }

        @Override
        protected Long initialValue() {
            return Long.valueOf(initialValue);
        }
    }
}
