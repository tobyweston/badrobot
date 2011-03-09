package bad.robot.pingpong.shared.memory.pessimistic;

import bad.robot.pingpong.server.Jmx;
import bad.robot.pingpong.shared.memory.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantLock;

import static bad.robot.pingpong.shared.memory.pessimistic.PessimisticExecutorServiceFactory.PessimisticThreadCounters.createLockBasedThreadSafeCounter;
import static bad.robot.pingpong.shared.memory.pessimistic.SynchronisingGuard.synchronised;
import static bad.robot.pingpong.shared.memory.pessimistic.Unguarded.unguarded;
import static java.util.concurrent.Executors.newFixedThreadPool;

public class PessimisticExecutorServiceFactory implements ExecutorServiceFactory {

    @Override
    public ExecutorService create() {
        ThreadObserver observer = createLockBasedThreadSafeCounter();
        Jmx.register(observer);
        return newFixedThreadPool(5, new ObservableThreadFactory(observer));
    }

    public static class PessimisticThreadCounters {

        static ThreadObserver createLockBasedThreadSafeCounter() {
            return new ThreadCounter(new LockingGuard(new ReentrantLock()), new AtomicLongCounter(), new AtomicLongCounter());
        }

        static ThreadObserver createSynchronisedCounter() {
            return new ThreadCounter(synchronised(), new LongCounter(), new LongCounter());
        }

        static ThreadObserver createNonThreadSafeCounter() {
            return new ThreadCounter(unguarded(), new LongCounter(), new LongCounter());
        }

        static ThreadObserver createThreadSafeCounterWithoutMaintainingResetInvariant() {
            return new ThreadCounter(unguarded(), new AtomicLongCounter(), new AtomicLongCounter());
        }
    }
}
