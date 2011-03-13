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

import bad.robot.pingpong.Introduce;
import bad.robot.pingpong.shared.memory.pessimistic.AtomicLongCounter;
import bad.robot.pingpong.shared.memory.pessimistic.MillisecondCounter;
import bad.robot.pingpong.shared.memory.pessimistic.ThreadLocalStopWatch;
import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;

import static java.lang.Thread.currentThread;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ThreadPoolTimerIntegrationTest {

    private static ThreadPoolTimer timer = new ThreadPoolTimer(new ThreadLocalStopWatch(new RealClock()), new AtomicLongCounter(), new AtomicLongCounter(), new MillisecondCounter());
    private static final Throwable NO_EXCEPTION = null;

    @Rule public ConcurrentRule concurrent = new ConcurrentRule();
    @Rule public RepeatingRule repeating = new RepeatingRule();

    @Concurrent (count = 50)
    @Repeating (repetition = 100)
    @Test
    public void executeTask() {
        Runnable task = newRunnable();
        timer.beforeExecute(currentThread(), task);
        Introduce.jitter();
        timer.afterExecute(task, NO_EXCEPTION);
    }

    @AfterClass
    public static void verifyCounters() {
        assertThat(timer.getNumberOfExecutions(), is(5000L));
        assertThat(timer.getMeanExecutionTime(), is(2L));
        assertThat(timer.getTerminated(), is(0L));
    }

    private static Runnable newRunnable() {
        return new Runnable() {
            @Override
            public void run() {
            }
        };
    }
}
