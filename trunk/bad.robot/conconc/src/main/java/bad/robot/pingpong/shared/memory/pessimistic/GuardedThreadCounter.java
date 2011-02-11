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

package bad.robot.pingpong.shared.memory.pessimistic;

import bad.robot.pingpong.UncheckedException;
import com.google.code.tempusfugit.concurrency.Callable;
import com.google.code.tempusfugit.concurrency.Interruptible;
import com.google.code.tempusfugit.concurrency.annotations.Not;
import com.google.code.tempusfugit.concurrency.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.google.code.tempusfugit.concurrency.ExecuteUsingLock.execute;
import static com.google.code.tempusfugit.concurrency.ThreadUtils.resetInterruptFlagWhen;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Not(ThreadSafe.class)
public class GuardedThreadCounter implements bad.robot.pingpong.shared.memory.ThreadCounter {

    private final AtomicLong activeThreads = new AtomicLong();
    private final AtomicLong threadCount = new AtomicLong();
    private final Lock lock = new ReentrantLock();

    private final Callable<Long, UncheckedException> incrementActiveThreads = new Callable<Long, UncheckedException>() {
        @Override
        public Long call() throws UncheckedException {
            return activeThreads.getAndIncrement();
        }
    };

    private Callable<Long, UncheckedException> decrementActiveThreads = new Callable<Long, UncheckedException>() {
        @Override
        public Long call() throws UncheckedException {
            return activeThreads.getAndDecrement();
        }
    };

    private final Callable<Void, UncheckedException> incrementCreatedThreads = new Callable<Void, UncheckedException>() {
        @Override
        public Void call() throws UncheckedException {
            threadCount.getAndIncrement();
            return null;
        }
    };

    private final Callable<Boolean, UncheckedException> reset = new Callable<Boolean, UncheckedException>() {
        @Override
        public Boolean call() throws UncheckedException {
            threadCount.set(0);
            activeThreads.set(0);
            return true;
        }
    };

    @Override
    public void incrementActiveThreads() {
        execute(incrementActiveThreads).using(lock);
    }

    @Override
    public void decrementActiveThreads() {
        execute(decrementActiveThreads).using(lock);
    }

    @Override
    public void incrementCreatedThreads() {
        execute(incrementCreatedThreads).using(lock);
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
        if (acquired(lock))
            execute(reset).using(lock);
    }

    private static Boolean acquired(final Lock lock) {
        return resetInterruptFlagWhen(new Interruptible<Boolean>() {
            @Override
            public Boolean call() throws InterruptedException {
                return lock.tryLock(10, MILLISECONDS);
            }
        });
    }


}
