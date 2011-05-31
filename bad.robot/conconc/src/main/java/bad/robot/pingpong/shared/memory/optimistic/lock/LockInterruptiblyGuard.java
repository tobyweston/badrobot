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

package bad.robot.pingpong.shared.memory.optimistic.lock;

import bad.robot.pingpong.shared.memory.Guard;
import com.google.code.tempusfugit.concurrency.Callable;

import java.util.concurrent.locks.Lock;

import static bad.robot.pingpong.shared.memory.optimistic.lock.AcquireLock.acquired;

/**
 * The {@link LockInterruptiblyGuard} can be used as acquire a lock
 * in a <i>non-blocking</i> way. When preceding the {@link #execute(com.google.code.tempusfugit.concurrency.Callable)}
 * with a call to {@link #guarding()} or via an interruption when waiting to block
 * (in the {@link #execute(com.google.code.tempusfugit.concurrency.Callable)} call). If the attempt to lock fails due
 * to interruption, the underlying {@link Callable} is <b>not</b> executed and <code>null</code> is returned.
 */
public class LockInterruptiblyGuard implements Guard {

    private final Lock lock;

    public LockInterruptiblyGuard(Lock lock) {
        this.lock = lock;
    }

    @Override
    public <R, E extends Exception> R execute(Callable<R, E> callable) throws E {
        try {
            lock.lockInterruptibly();
            return callable.call();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Boolean guarding() {
        return acquired(lock);
    }
}
