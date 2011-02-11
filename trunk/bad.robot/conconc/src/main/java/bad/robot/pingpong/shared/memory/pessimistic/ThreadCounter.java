package bad.robot.pingpong.shared.memory.pessimistic;

import bad.robot.pingpong.shared.memory.ThreadCount;
import bad.robot.pingpong.shared.memory.ThreadFactoryObserver;

public class ThreadCounter implements ThreadFactoryObserver, ThreadCount {

    private long activeThreads;
    private long createdThreads;

    @Override
    public void threadCreated() {
        createdThreads++;
    }

    @Override
    public void threadStarted() {
        activeThreads++;
    }

    @Override
    public void threadTerminated() {
        activeThreads--;
    }

    @Override
    public long getActiveCount() {
        return activeThreads;
    }

    @Override
    public long getCreatedCount() {
        return createdThreads;
    }

    @Override
    public void reset() {
        activeThreads = 0;
        createdThreads = 0;
    }
}
