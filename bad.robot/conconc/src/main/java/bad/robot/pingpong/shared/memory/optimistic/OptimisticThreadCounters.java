/*
 * Copyright (c) 2009-2011, bad robot (london) ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bad.robot.pingpong.shared.memory.optimistic;

import bad.robot.pingpong.shared.memory.ThreadCounter;
import bad.robot.pingpong.shared.memory.ThreadObserver;
import bad.robot.pingpong.shared.memory.optimistic.atomic.AtomicLongCounter;
import bad.robot.pingpong.shared.memory.optimistic.lock.LockAttemptingGuard;
import bad.robot.pingpong.shared.memory.optimistic.lock.LockInterruptiblyGuard;
import bad.robot.pingpong.shared.memory.optimistic.stm.StmAtomicLongCounter;
import bad.robot.pingpong.shared.memory.optimistic.stm.StmGuard;
import bad.robot.pingpong.shared.memory.optimistic.stm.TransactionalReferenceCounter;

import java.util.concurrent.locks.ReentrantLock;

import static bad.robot.pingpong.shared.memory.Unguarded.unguarded;

public class OptimisticThreadCounters {

    public static class Conventional {

        public static ThreadObserver createNonBlockingThreadSafeCounterMaintainingInvariant() {
            return new ThreadCounter(new LockAttemptingGuard(new ReentrantLock()), new AtomicLongCounter(), new AtomicLongCounter());
        }

        public static ThreadObserver createPotentiallyNonBlockingThreadSafeCounterMaintainingInvariant() {
            return new ThreadCounter(new LockInterruptiblyGuard(new ReentrantLock()), new AtomicLongCounter(), new AtomicLongCounter());
        }

        public static ThreadObserver createNonThreadSafeCounter() {
            return new ThreadCounter(unguarded(), new LongCounter(), new LongCounter());
        }

        public static ThreadObserver createThreadSafeCounterWithoutMaintainingResetInvariant() {
            return new ThreadCounter(unguarded(), new AtomicLongCounter(), new AtomicLongCounter());
        }
    }

    public static class Stm {

        static ThreadObserver createThreadSafeCounterMaintainingInvariant() {
            return new ThreadCounter(new StmGuard(), new TransactionalReferenceCounter(), new TransactionalReferenceCounter());
        }

        static ThreadObserver createThreadSafeStmCounterWithoutMaintainingResetInvariant() {
            return new ThreadCounter(unguarded(), new StmAtomicLongCounter(), new StmAtomicLongCounter());
        }

        static ThreadObserver createNonThreadSafeStmCounter() {
            return new ThreadCounter(unguarded(), new TransactionalReferenceCounter(), new TransactionalReferenceCounter());
        }
    }
}
