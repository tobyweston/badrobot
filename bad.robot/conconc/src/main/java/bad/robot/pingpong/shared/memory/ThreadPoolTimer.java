package bad.robot.pingpong.shared.memory;

import bad.robot.pingpong.StopWatch;
import com.google.code.tempusfugit.temporal.Duration;

public class ThreadPoolTimer implements ThreadPoolObserver, ThreadPoolTimerMBean {

    private final StopWatch timer;
    private final Counter tasks;
    private final Counter terminated;
    private final AccumulatingCounter<Duration> totalTime;

    public ThreadPoolTimer(StopWatch timer, Counter tasks, Counter terminated, AccumulatingCounter<Duration> totalTime) {
        this.timer = timer;
        this.tasks = tasks;
        this.terminated = terminated;
        this.totalTime = totalTime;
    }

    @Override
    public void beforeExecute(Thread thread, Runnable task) {
        assert(Thread.currentThread().equals(thread));
        timer.start();
        tasks.increment();
    }

    @Override
    public void afterExecute(Runnable task, Throwable throwable) {
        timer.stop();
        totalTime.add(timer.elapsedTime());
    }

    @Override
    public void terminated() {
        terminated.increment();

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
        totalTime.reset();
        tasks.reset();
        terminated.reset();
    }

}
