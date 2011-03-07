package bad.robot.pingpong.shared.memory.pessimistic;

import bad.robot.pingpong.shared.memory.ExecutorServiceFactory;
import bad.robot.pingpong.shared.memory.LongCounter;
import bad.robot.pingpong.shared.memory.ObservableThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantLock;

import static bad.robot.pingpong.shared.memory.pessimistic.PessimisticExecutorServiceFactory.ThreadCounterBuilder.createLockBasedThreadSafeCounter;
import static bad.robot.pingpong.shared.memory.pessimistic.SynchronisingGuard.synchronised;
import static bad.robot.pingpong.shared.memory.pessimistic.Unguarded.unguarded;
import static java.util.concurrent.Executors.newFixedThreadPool;

public class PessimisticExecutorServiceFactory implements ExecutorServiceFactory {

    @Override
    public ExecutorService create() {
        return newFixedThreadPool(5, new ObservableThreadFactory(createLockBasedThreadSafeCounter()));
    }

    public static class ThreadCounterBuilder {
        static ThreadCounter createLockBasedThreadSafeCounter() {
            return new ThreadCounter(new LockingGuard(new ReentrantLock()), new AtomicLongCounter(), new AtomicLongCounter());
        }

        static ThreadCounter createSynchronisedCounter() {
            return new ThreadCounter(synchronised(), new LongCounter(), new LongCounter());
        }

        static ThreadCounter createNonThreadSafeCounter() {
            return new ThreadCounter(unguarded(), new LongCounter(), new LongCounter());
        }

        static ThreadCounter createThreadSafeCounterWithoutMaintainingResetInvariant() {
            return new ThreadCounter(unguarded(), new AtomicLongCounter(), new AtomicLongCounter());
        }
    }
}
