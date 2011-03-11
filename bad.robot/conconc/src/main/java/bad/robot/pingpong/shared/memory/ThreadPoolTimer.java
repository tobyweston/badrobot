package bad.robot.pingpong.shared.memory;

import bad.robot.pingpong.shared.memory.pessimistic.ThreadLocalStopWatch;

import java.util.concurrent.atomic.AtomicLong;

public class ThreadPoolTimer implements ThreadPoolObserver, ThreadPoolTimerMBean {

    private final ThreadLocalStopWatch timer = new ThreadLocalStopWatch(new RealClock());
    private final AtomicLong tasks = new AtomicLong(0);
    private final AtomicLong totalTime = new AtomicLong(0);

    @Override
    public void beforeExecute(Thread thread, Runnable task) {
        timer.start();
        tasks.incrementAndGet();
    }

    @Override
    public void afterExecute(Runnable task, Throwable throwable) {
        timer.stop();
        totalTime.addAndGet(timer.elapsedTime().inMillis());
    }

    @Override
    public void terminated() {

    }

    @Override
    public Long getMeanExecutionTime() {
        return totalTime.get() / tasks.get();
    }

    @Override
    public void reset() {
        totalTime.set(0L);
        tasks.set(0L);
        timer.start();
    }
}
