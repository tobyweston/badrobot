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

package bad.robot.pingpong.shared.memory;

import bad.robot.pingpong.shared.memory.pessimistic.AtomicLongCounter;
import bad.robot.pingpong.shared.memory.pessimistic.AtomicMillisecondCounter;
import bad.robot.pingpong.shared.memory.pessimistic.LockingGuard;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

import static bad.robot.pingpong.shared.memory.ThreadPoolTimerMeanExecutionTimeMatcher.hasMeanElapsedTimeOf;
import static com.google.code.tempusfugit.temporal.Duration.millis;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ThreadPoolTimerTest {

    private static final Throwable NO_EXCEPTION = null;

    private final StopWatchStub stopWatch = new StopWatchStub();
    private final ThreadPoolTimer timer = new ThreadPoolTimer(new LockingGuard(new ReentrantLock()), stopWatch, new AtomicLongCounter(), new AtomicLongCounter(), new AtomicMillisecondCounter());

    @Test
    public void shouldGetMeanExecutionTime() {
        timer.beforeExecute(newThread(), newRunnable());
        timer.afterExecute(newRunnable(), NO_EXCEPTION);
        assertThat(timer, hasMeanElapsedTimeOf(millis(0)));

        stopWatch.setElapsedTime(millis(500));
        timer.beforeExecute(newThread(), newRunnable());
        timer.afterExecute(newRunnable(), NO_EXCEPTION);
        assertThat(timer, hasMeanElapsedTimeOf(millis(250)));

        stopWatch.setElapsedTime(millis(1500));
        timer.beforeExecute(newThread(), newRunnable());
        timer.afterExecute(newRunnable(), NO_EXCEPTION);
        assertThat(timer, hasMeanElapsedTimeOf(millis(666)));

        stopWatch.setElapsedTime(millis(600));
        timer.beforeExecute(newThread(), newRunnable());
        timer.afterExecute(newRunnable(), NO_EXCEPTION);
        assertThat(timer, hasMeanElapsedTimeOf(millis(650)));
    }

    @Test
    public void shouldMaintainMeanForDifferentThreads() throws InterruptedException {
        callTimerInAnotherThread();
        assertThat(timer, hasMeanElapsedTimeOf(millis(0)));
        stopWatch.setElapsedTime(millis(500));
        callTimerInAnotherThread();
        assertThat(timer, hasMeanElapsedTimeOf(millis(250)));
        stopWatch.setElapsedTime(millis(1500));
        callTimerInAnotherThread();
        assertThat(timer, hasMeanElapsedTimeOf(millis(666)));
    }

    private void callTimerInAnotherThread() throws InterruptedException {
        final CountDownLatch complete = new CountDownLatch(1);
        new Thread() {
            @Override
            public void run() {
                timer.beforeExecute(this, newRunnable());
                timer.afterExecute(newRunnable(), NO_EXCEPTION);
                complete.countDown();
            }
        }.start();
        assertThat(complete.await(500, MILLISECONDS), is(true));
    }

    private static Thread newThread() {
        return new Thread();
    }

    private static Runnable newRunnable() {
        return new Runnable() {
            @Override
            public void run() {
            }
        };
    }

}
