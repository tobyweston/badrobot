package bad.robot.pingpong.shared.memory.optimistic;

import bad.robot.pingpong.shared.memory.ThreadCounter;
import bad.robot.pingpong.shared.memory.ThreadObserver;

import static bad.robot.pingpong.shared.memory.pessimistic.Unguarded.unguarded;

public class OptimisticThreadCounters {

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
