package bad.robot.pingpong.shared.memory.optimistic;

import bad.robot.pingpong.server.Jmx;
import bad.robot.pingpong.shared.memory.ExecutorServiceFactory;
import bad.robot.pingpong.shared.memory.ObservableThreadFactory;
import bad.robot.pingpong.shared.memory.ThreadCounter;
import bad.robot.pingpong.shared.memory.ThreadObserver;

import java.util.concurrent.ExecutorService;

import static bad.robot.pingpong.shared.memory.optimistic.OptimisticExecutorServiceFactory.OptimisticThreadCounters.createThreadSafeCounterMaintainingInvariant;
import static bad.robot.pingpong.shared.memory.pessimistic.Unguarded.unguarded;
import static java.util.concurrent.Executors.newFixedThreadPool;

public class OptimisticExecutorServiceFactory implements ExecutorServiceFactory {

    @Override
    public ExecutorService create() {
        ThreadObserver observer = createThreadSafeCounterMaintainingInvariant();
        Jmx.register(observer);
        return newFixedThreadPool(5, new ObservableThreadFactory(observer));
    }

    public static class OptimisticThreadCounters {

        static ThreadObserver createThreadSafeCounterMaintainingInvariant() {
            return new ThreadCounter(new StmGuard(), new TransactionalReferenceCounter(), new TransactionalReferenceCounter());
        }

        static ThreadObserver createThreadSafeCounterWithoutMaintainingResetInvariant() {
            return new ThreadCounter(unguarded(), new StmAtomicLongCounter(), new StmAtomicLongCounter());
        }

        static ThreadObserver createNonThreadSafeCounter() {
            return new ThreadCounter(unguarded(), new TransactionalReferenceCounter(), new TransactionalReferenceCounter());
        }
    }

}
