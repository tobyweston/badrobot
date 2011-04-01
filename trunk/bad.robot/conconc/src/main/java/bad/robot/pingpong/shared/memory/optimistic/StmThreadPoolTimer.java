package bad.robot.pingpong.shared.memory.optimistic;

import akka.stm.Atomic;
import akka.stm.Ref;
import akka.stm.TransactionFactory;
import akka.stm.TransactionFactoryBuilder;
import bad.robot.pingpong.StopWatch;
import bad.robot.pingpong.shared.memory.ThreadPoolObserver;
import bad.robot.pingpong.shared.memory.ThreadPoolTimerMBean;

public class StmThreadPoolTimer implements ThreadPoolObserver, ThreadPoolTimerMBean {

    private final StopWatch timer;
    private final Ref<Long> tasks = new Ref<Long>(0L);
    private final Ref<Long> terminated = new Ref<Long>(0L);
    private final Ref<Long> totalTime = new Ref<Long>(0L);

    public StmThreadPoolTimer(StopWatch timer) {
        this.timer = timer;
    }

    @Override
    public void beforeExecute(Thread thread, Runnable task) {
        assert(Thread.currentThread().equals(thread));
        timer.start();
        new Atomic<Void>() {
            @Override
            public Void atomically() {
                tasks.set(tasks.get() + 1L);
                return null;
            }
        }.execute();
    }

    @Override
    public void afterExecute(Runnable task, Throwable throwable) {
        timer.stop();
        final long elapsed = timer.elapsedTime().inMillis();
        new Atomic<Void>() {
            @Override
            public Void atomically() {
                totalTime.set(totalTime.get() + elapsed);
                return null;
            }
        }.execute();
    }

    @Override
    public void terminated() {
        new Atomic<Void>() {
            @Override
            public Void atomically() {
                terminated.set(terminated.get() + 1L);
                return null;
            }
        }.execute();
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
        TransactionFactory factory = new TransactionFactoryBuilder().setReadonly(true).build();
        return new Atomic<Long>(factory) {
            @Override
            public Long atomically() {
                return totalTime.get() / tasks.get();
            }
        }.execute();
    }

    @Override
    public Long getTerminated() {
        return terminated.get();
    }

    @Override
    public void reset() {
    }


}
