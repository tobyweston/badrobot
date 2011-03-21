package bad.robot.pingpong.shared.memory.pessimistic;

import bad.robot.pingpong.shared.memory.LongCounter;
import bad.robot.pingpong.shared.memory.ThreadCounter;
import bad.robot.pingpong.shared.memory.ThreadObserver;

import java.util.concurrent.locks.ReentrantLock;

import static bad.robot.pingpong.shared.memory.pessimistic.SynchronisingGuard.synchronised;
import static bad.robot.pingpong.shared.memory.pessimistic.Unguarded.unguarded;

public class PessimisticThreadCounters {

    public static ThreadObserver createThreadSafeCounterMaintainingInvariant() {
        return new ThreadCounter(new LockingGuard(new ReentrantLock()), new AtomicLongCounter(), new AtomicLongCounter());
    }

    public static ThreadObserver createSynchronisedThreadSafeCounter() {
        return new ThreadCounter(synchronised(), new LongCounter(), new LongCounter());
    }

    public static ThreadObserver createNonThreadSafeCounter() {
        return new ThreadCounter(unguarded(), new LongCounter(), new LongCounter());
    }

    public static ThreadObserver createThreadSafeCounterWithoutMaintainingResetInvariant() {
        return new ThreadCounter(unguarded(), new AtomicLongCounter(), new AtomicLongCounter());
    }
}
