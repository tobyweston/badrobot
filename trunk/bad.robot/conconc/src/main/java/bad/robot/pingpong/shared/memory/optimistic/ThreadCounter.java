package bad.robot.pingpong.shared.memory.optimistic;


import akka.stm.Atomic;
import akka.stm.Ref;
import bad.robot.pingpong.shared.memory.ThreadObserver;

public class ThreadCounter implements ThreadObserver {

    private final Ref<Long> createdThreads = new Ref<Long>(0L);
    private final Ref<Long> activeThreads = new Ref<Long>(0L);

    @Override
    public void threadCreated() {
        new Atomic<Long>() {
            @Override
            public Long atomically() {
                return createdThreads.set(createdThreads.get() + 1);
            }
        }.execute();
    }

    @Override
    public void threadStarted() {
        new Atomic<Long>() {
            @Override
            public Long atomically() {
                return activeThreads.set(activeThreads.get() + 1);
            }
        }.execute();
    }

    @Override
    public void threadTerminated() {
        new Atomic<Long>() {
            @Override
            public Long atomically() {
                return activeThreads.set(activeThreads.get() - 1);
            }
        }.execute();
    }

    public Long getActiveThreads() {
        return activeThreads.get();
    }

    public Long getCreatedThreads() {
        return createdThreads.get();
    }

    public void reset() {
        new Atomic<Void>() {
            @Override
            public Void atomically() {
                createdThreads.set(0L);
                activeThreads.set(0L);
                return null;
            }
        }.execute();
    }
}
