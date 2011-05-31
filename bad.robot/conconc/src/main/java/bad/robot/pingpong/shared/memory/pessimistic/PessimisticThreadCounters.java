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

package bad.robot.pingpong.shared.memory.pessimistic;

import bad.robot.pingpong.shared.memory.ThreadCounter;
import bad.robot.pingpong.shared.memory.ThreadObserver;
import bad.robot.pingpong.shared.memory.optimistic.LongCounter;
import bad.robot.pingpong.shared.memory.optimistic.atomic.AtomicLongCounter;
import bad.robot.pingpong.shared.memory.optimistic.lock.LockingGuard;

import java.util.concurrent.locks.ReentrantLock;

import static bad.robot.pingpong.shared.memory.pessimistic.synchronised.SynchronisingGuard.synchronised;

public class PessimisticThreadCounters {

    /**
     * Because the {@link LockingGuard} is called without first checking the lock can be acquired (ie, it doesn't call
     * <code>tryLock</code> before guarding), it has been classified pessimistic (even though, the counters themselves
     * are optimistic).
     */
    public static ThreadObserver createThreadSafeCounterMaintainingInvariant() {
        return new ThreadCounter(new LockingGuard(new ReentrantLock()), new AtomicLongCounter(), new AtomicLongCounter());
    }

    public static ThreadObserver createSynchronisedThreadSafeCounter() {
        return new ThreadCounter(synchronised(), new LongCounter(), new LongCounter());
    }

}
