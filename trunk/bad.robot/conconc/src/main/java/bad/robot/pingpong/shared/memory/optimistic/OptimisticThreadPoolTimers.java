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

import bad.robot.pingpong.shared.memory.RealClock;
import bad.robot.pingpong.shared.memory.ThreadLocalStopWatch;
import bad.robot.pingpong.shared.memory.ThreadPoolTimer;
import bad.robot.pingpong.shared.memory.optimistic.atomic.AtomicLongCounter;
import bad.robot.pingpong.shared.memory.optimistic.atomic.AtomicMillisecondCounter;
import bad.robot.pingpong.shared.memory.optimistic.lock.LockAttemptingGuard;
import bad.robot.pingpong.shared.memory.optimistic.lock.LockInterruptiblyGuard;
import bad.robot.pingpong.shared.memory.optimistic.stm.StmAtomicLongCounter;
import bad.robot.pingpong.shared.memory.optimistic.stm.StmGuard;
import bad.robot.pingpong.shared.memory.optimistic.stm.StmMillisecondCounter;
import bad.robot.pingpong.shared.memory.optimistic.stm.TransactionalReferenceMillisecondCounter;

import java.util.concurrent.locks.ReentrantLock;

import static bad.robot.pingpong.shared.memory.Unguarded.unguarded;

public class OptimisticThreadPoolTimers {

    public static class Conventional {

        public static ThreadPoolTimer createNonBlockingThreadSafeThreadPoolTimer() {
            return new ThreadPoolTimer(new LockAttemptingGuard(new ReentrantLock()), new ThreadLocalStopWatch(new RealClock()), new AtomicLongCounter(), new AtomicLongCounter(), new AtomicMillisecondCounter());
        }

        public static ThreadPoolTimer createPotentiallyNonBlockingThreadSafeThreadPoolTimer() {
            return new ThreadPoolTimer(new LockInterruptiblyGuard(new ReentrantLock()), new ThreadLocalStopWatch(new RealClock()), new AtomicLongCounter(), new AtomicLongCounter(), new AtomicMillisecondCounter());
        }

        public static ThreadPoolTimer createThreadSafeThreadPoolTimerWithoutEnforcingRaceConditionConsistency() {
            return new ThreadPoolTimer(unguarded(), new ThreadLocalStopWatch(new RealClock()), new AtomicLongCounter(), new AtomicLongCounter(), new AtomicMillisecondCounter());
        }

    }

    public static class Stm {

        public static ThreadPoolTimer createThreadSafeThreadPoolTimer() {
            return new ThreadPoolTimer(new StmGuard(), new ThreadLocalStopWatch(new RealClock()), new StmAtomicLongCounter(), new StmAtomicLongCounter(), new StmMillisecondCounter());
        }

        /**
         * This is thread unsafe because the {@link TransactionalReferenceMillisecondCounter} parameter is left unguarded in
         * the call to {@link ThreadPoolTimer#afterExecute(Runnable, Throwable)}. Although it is a transactional reference
         * and so protected in the {@link bad.robot.pingpong.shared.memory.ThreadPoolTimer#getMeanExecutionTime()} method,
         * it is left unprotected in the previous call.
         */
        public static ThreadPoolTimer createThreadUnsafeThreadPoolTimer() {
            return new ThreadPoolTimer(new StmGuard(), new ThreadLocalStopWatch(new RealClock()), new StmAtomicLongCounter(), new StmAtomicLongCounter(), new TransactionalReferenceMillisecondCounter());
        }

        /**
         * This should be unsafe because without the guard, the race condition on {@link bad.robot.pingpong.shared.memory.ThreadPoolTimer#getMeanExecutionTime()}
         * isn't protected. However, this is difficult to test
         */
        public static ThreadPoolTimer createUnsafeGetMeanExecutionTimeThreadPoolTimer() {
            return new ThreadPoolTimer(unguarded(), new ThreadLocalStopWatch(new RealClock()), new StmAtomicLongCounter(), new StmAtomicLongCounter(), new StmMillisecondCounter());
        }

    }

}
