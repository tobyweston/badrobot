/*
 * Copyright (c) 2009-2010, bad robot (london) ltd
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

package bad.robot.pingpong.memory.shared.pessimistic;

import bad.robot.pingpong.memory.shared.ThreadCounter;
import com.google.code.tempusfugit.concurrency.annotations.Not;
import com.google.code.tempusfugit.concurrency.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicLong;

@Not(ThreadSafe.class)
public class GuardedThreadCounter implements ThreadCounter {

    private final AtomicLong activeThreads = new AtomicLong();
    private final AtomicLong threadCount = new AtomicLong();

    @Override
    public void incrementActiveThreads() {
        activeThreads.getAndIncrement();
    }

    @Override
    public void decrementActiveThreads() {
        activeThreads.getAndDecrement();
    }

    @Override
    public void incrementThreadCount() {
        threadCount.getAndIncrement();
    }

    @Override
    public long getActiveThreads() {
        return activeThreads.get();
    }

    @Override
    public long getThreadCount() {
        return threadCount.get();
    }

    @Override
    public void reset() {
        threadCount.set(0);
        activeThreads.set(0);
    }


}
