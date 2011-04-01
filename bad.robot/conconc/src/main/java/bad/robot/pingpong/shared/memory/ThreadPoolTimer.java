package bad.robot.pingpong.shared.memory;

import bad.robot.pingpong.StopWatch;
import bad.robot.pingpong.shared.memory.pessimistic.Guard;
import com.google.code.tempusfugit.temporal.Duration;

import static bad.robot.pingpong.shared.memory.Divide.Divisor.by;
import static bad.robot.pingpong.shared.memory.Divide.divide;

public class ThreadPoolTimer implements ThreadPoolObserver, ThreadPoolTimerMBean {

    private final Guard guard;
    private final StopWatch timer;
    private final Counter tasks;
    private final Counter terminated;
    private final AccumulatingCounter<Duration> totalTime;

    public ThreadPoolTimer(Guard guard, StopWatch timer, Counter tasks, Counter terminated, AccumulatingCounter<Duration> totalTime) {
        this.timer = timer;
        this.tasks = tasks;
        this.terminated = terminated;
        this.totalTime = totalTime;
        this.guard = guard;
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
        return guard.execute(divide(totalTime, by(tasks)));
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
