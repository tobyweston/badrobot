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
