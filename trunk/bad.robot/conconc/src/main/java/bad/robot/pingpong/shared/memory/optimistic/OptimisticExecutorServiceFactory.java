package bad.robot.pingpong.shared.memory.optimistic;

import bad.robot.pingpong.shared.memory.ExecutorServiceFactory;
import bad.robot.pingpong.shared.memory.ObservableThreadFactory;
import bad.robot.pingpong.shared.memory.pessimistic.ThreadCounter;

import java.util.concurrent.ExecutorService;

import static bad.robot.pingpong.shared.memory.optimistic.OptimisticExecutorServiceFactory.OptimisticThreadCounters.createThreadSafeCounterMaintainingInvariant;
import static bad.robot.pingpong.shared.memory.pessimistic.Unguarded.unguarded;
import static java.util.concurrent.Executors.newFixedThreadPool;

public class OptimisticExecutorServiceFactory implements ExecutorServiceFactory {

    @Override
    public ExecutorService create() {
        return newFixedThreadPool(5, new ObservableThreadFactory(createThreadSafeCounterMaintainingInvariant()));
    }

    public static class OptimisticThreadCounters {

        static ThreadCounter createThreadSafeCounterMaintainingInvariant() {
            return new ThreadCounter(new StmGuard(), new TransactionalReferenceCounter(), new TransactionalReferenceCounter());
        }

        static ThreadCounter createThreadSafeCounterWithoutMaintainingResetInvariant() {
            return new ThreadCounter(unguarded(), new StmAtomicLongCounter(), new StmAtomicLongCounter());
        }

        static ThreadCounter createNonThreadSafeCounter() {
            return new ThreadCounter(unguarded(), new TransactionalReferenceCounter(), new TransactionalReferenceCounter());
        }
    }
}
