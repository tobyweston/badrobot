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

import com.google.code.tempusfugit.concurrency.Callable;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@RunWith(JMock.class)
public class LockingGuardTest {

    private final Mockery context = new JUnit4Mockery();
    
    private final Lock lock = context.mock(Lock.class);

    @Test
    public void locksAndUnlocks() {
        setExpectationsOn(lock);
        new LockingGuard(lock).execute(something());
    }

    @Test(expected = Exception.class)
    public void locksAndUnlocksWhenExceptionThrown() throws Exception {
        setExpectationsOn(lock);
        new LockingGuard(lock).execute(somethingThatThrowsException());
    }

    @Test
    public void shouldAttemptToAcquireLock() throws InterruptedException {
        context.checking(new Expectations(){{
            one(lock).tryLock(with(any(Long.class)), with(any(TimeUnit.class)));
        }});
        new LockingGuard(lock).guarding();
    }

    private Callable<Void, RuntimeException> something() {
        return new Callable<Void, RuntimeException>() {
            public Void call() throws RuntimeException {
                return null;
            }
        };
    }

    private Callable<Void, Exception> somethingThatThrowsException() {
        return new Callable<Void, Exception>() {
            public Void call() throws Exception {
                throw new RuntimeException("go go go");
            }
        };
    }

    private void setExpectationsOn(final Lock lock) {
        context.checking(new Expectations() {{
            one(lock).lock();
            one(lock).unlock();
        }});
    }

}
