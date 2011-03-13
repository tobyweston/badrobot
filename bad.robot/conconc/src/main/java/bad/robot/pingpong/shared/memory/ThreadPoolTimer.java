package bad.robot.pingpong.shared.memory;

import bad.robot.pingpong.StopWatch;

import java.util.concurrent.atomic.AtomicLong;

public class ThreadPoolTimer implements ThreadPoolObserver, ThreadPoolTimerMBean {

    private final StopWatch timer;
    private final AtomicLong tasks;
    private final AtomicLong totalTime;
    private final AtomicLong terminated;

    public ThreadPoolTimer(StopWatch timer) {
        this.timer = timer;
        tasks = new AtomicLong(0);
        totalTime = new AtomicLong(0);
        terminated = new AtomicLong(0);
    }

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
        terminated.incrementAndGet();

    }

    @Override
    public Long getNumberOfExecutions() {
        return tasks.get();
    }

    @Override
    public Long getTotalTime() {
        return totalTime.get();
    }

    @Override
    public Long getMeanExecutionTime() {
        return totalTime.get() / tasks.get();
    }

    @Override
    public Long getTerminated() {
        return terminated.get();
    }

    @Override
    public void reset() {
        totalTime.set(0L);
        tasks.set(0L);
    }

}
