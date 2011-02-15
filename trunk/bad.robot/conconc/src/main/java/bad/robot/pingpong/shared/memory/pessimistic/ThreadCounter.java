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

import bad.robot.pingpong.shared.memory.ThreadCount;
import bad.robot.pingpong.shared.memory.ThreadFactoryObserver;
import com.google.code.tempusfugit.concurrency.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicLong;

import static bad.robot.pingpong.shared.memory.pessimistic.Decrement.decrement;
import static bad.robot.pingpong.shared.memory.pessimistic.Increment.increment;
import static bad.robot.pingpong.shared.memory.pessimistic.Reset.resetOf;

@ThreadSafe
public class ThreadCounter implements ThreadFactoryObserver, ThreadCount {

    private final AtomicLong activeThreads = new AtomicLong();
    private final AtomicLong createdThreads = new AtomicLong();
    private final Guard guard;

    public ThreadCounter(Guard guard) {
        this.guard = guard;
    }

    @Override
    public void threadCreated() {
        guard.execute(increment(createdThreads));
    }

    @Override
    public void threadStarted() {
        guard.execute(increment(activeThreads));
    }

    @Override
    public void threadTerminated() {
        guard.execute(decrement(activeThreads));
    }

    @Override
    public long getActiveCount() {
        return activeThreads.get();
    }

    @Override
    public long getCreatedCount() {
        return createdThreads.get();
    }

    @Override
    public void reset() {
        if (guard.guarding())
            guard.execute(resetOf(activeThreads, createdThreads));
    }

}