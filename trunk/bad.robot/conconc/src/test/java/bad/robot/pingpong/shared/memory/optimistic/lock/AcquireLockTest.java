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

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import static java.lang.Thread.currentThread;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(JMock.class)
public class AcquireLockTest {

    private final Mockery context = new Mockery();
    private final Lock lock = context.mock(Lock.class);

    @Test
    public void shouldAcquireLock() throws InterruptedException {
        context.checking(new Expectations(){{
            one(lock).tryLock(with(any(Long.class)), with(any(TimeUnit.class))); will(returnValue(true));
        }});
        assertThat(AcquireLock.acquired(lock), is(true));
    }

    @Test
    public void interruptFlagIsResetOnInterruption() throws InterruptedException {
        context.checking(new Expectations(){{
            one(lock).tryLock(with(any(Long.class)), with(any(TimeUnit.class))); will(throwException(new InterruptedException()));
        }});
        assertThat(currentThread().isInterrupted(), is(false));
        AcquireLock.acquired(lock);
        assertThat(currentThread().isInterrupted(), is(true));
    }
}
