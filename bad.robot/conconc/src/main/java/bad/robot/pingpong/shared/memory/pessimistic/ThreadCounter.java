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
import com.google.code.tempusfugit.concurrency.Callable;
import com.google.code.tempusfugit.concurrency.Interruptible;
import com.google.code.tempusfugit.concurrency.annotations.ThreadSafe;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.google.code.tempusfugit.concurrency.ExecuteUsingLock.execute;
import static com.google.code.tempusfugit.concurrency.ThreadUtils.resetInterruptFlagWhen;

@ThreadSafe
public class ThreadCounter implements ThreadFactoryObserver, ThreadCount {

    private final AtomicLong activeThreads = new AtomicLong();
    private final AtomicLong createdThreads = new AtomicLong();
    private final ReentrantLock lock = new ReentrantLock();

    @Override
    public void threadCreated() {
        execute(threadCreated).using(lock);
    }

    @Override
    public void threadStarted() {
        execute(threadStarted).using(lock);
    }

    @Override
    public void threadTerminated() {
        execute(threadTerminated).using(lock);
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
        if (acquired(lock))
            execute(reset).using(lock);
    }

    private Callable<Void, RuntimeException> threadCreated = new Callable<Void, RuntimeException>() {
        @Override
        public Void call() throws RuntimeException {
            createdThreads.getAndIncrement();
            return null;
        }
    };

    private Callable<Void, RuntimeException> threadStarted = new Callable<Void, RuntimeException>() {
        @Override
        public Void call() throws RuntimeException {
            activeThreads.getAndIncrement();
            return null;
        }
    };

    private Callable<Void, RuntimeException> threadTerminated = new Callable<Void, RuntimeException>() {
        @Override
        public Void call() throws RuntimeException {
            activeThreads.getAndDecrement();
            return null;
        }
    };

    private Callable<Void, RuntimeException> reset = new Callable<Void, RuntimeException>() {
        @Override
        public Void call() throws RuntimeException {
            activeThreads.set(0);
            createdThreads.set(0);
            return null;
        }
    };

    private static Boolean acquired(final Lock lock) {
        return resetInterruptFlagWhen(new Interruptible<Boolean>() {
            @Override
            public Boolean call() throws InterruptedException {
                return lock.tryLock(10, TimeUnit.MILLISECONDS);
            }
        });
    }

}
