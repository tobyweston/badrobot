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

package bad.robot.pingpong.shared.memory.optimistic;

import bad.robot.pingpong.Introduce;
import bad.robot.pingpong.shared.memory.ThreadLocalMovableClock;
import bad.robot.pingpong.shared.memory.ThreadLocalStopWatch;
import bad.robot.pingpong.shared.memory.ThreadPoolTimer;
import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;

import static com.google.code.tempusfugit.temporal.Duration.millis;
import static java.lang.Thread.currentThread;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ThreadPoolTimerIntegrationTest {

    private static final ThreadLocalMovableClock clock = new ThreadLocalMovableClock();
    private static final ThreadLocalStopWatch watch = new ThreadLocalStopWatch(clock);
    private static final ThreadPoolTimer timer = new ThreadPoolTimer(new StmGuard(), watch, new StmAtomicLongCounter(), new StmAtomicLongCounter(), new TransactionalReferenceMillisecondCounter());
    private static final Throwable NO_EXCEPTION = null;

    @Rule public ConcurrentRule concurrent = new ConcurrentRule();
    @Rule public RepeatingRule repeating = new RepeatingRule();

    @Concurrent (count = 50)
    @Repeating (repetition = 100)
    @Test
    public void executeTask() {
        Runnable task = newRunnable();
        timer.beforeExecute(currentThread(), task);
        clock.incrementBy(millis(500));
        timer.afterExecute(task, NO_EXCEPTION);
        Introduce.jitter();
    }

    @AfterClass
    public static void verifyCounters() {
//        System.out.println(String.format("(%s)", 100 * 50 * 500));
//        System.out.println(String.format("%s / %s = %s", timer.getTotalTime(), timer.getNumberOfExecutions(), timer.getMeanExecutionTime()));
        assertThat(timer.getNumberOfExecutions(), is(5000L));
        assertThat(timer.getMeanExecutionTime(), is(500L));
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
