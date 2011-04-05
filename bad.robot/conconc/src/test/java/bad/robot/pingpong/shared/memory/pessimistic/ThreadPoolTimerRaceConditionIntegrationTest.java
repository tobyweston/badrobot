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

import bad.robot.pingpong.Introduce;
import bad.robot.pingpong.shared.memory.ThreadLocalMovableClock;
import bad.robot.pingpong.shared.memory.ThreadLocalStopWatch;
import bad.robot.pingpong.shared.memory.ThreadPoolTimer;
import com.google.code.tempusfugit.temporal.Duration;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static bad.robot.pingpong.shared.memory.pessimistic.Unguarded.unguarded;
import static com.google.code.tempusfugit.concurrency.ExecutorServiceShutdown.shutdown;
import static com.google.code.tempusfugit.temporal.Duration.millis;
import static com.google.code.tempusfugit.temporal.Duration.seconds;
import static java.lang.Thread.currentThread;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ThreadPoolTimerRaceConditionIntegrationTest {

    private static final int threadCount = 50;
    private static final int repetitions = 100;

    private static final ThreadLocalMovableClock clock = new ThreadLocalMovableClock();
    private static final ThreadPoolTimer timer = new ThreadPoolTimer(unguarded(), new ThreadLocalStopWatch(clock), new AtomicLongCounter(), new AtomicLongCounter(), new AtomicMillisecondCounter());
    private static final Throwable NO_EXCEPTION = null;

    @Test
    public void executeTask() throws InterruptedException, ExecutionException {
        List<Future<?>> futures = new ArrayList<Future<?>>();
        ExecutorService pool = newFixedThreadPool(threadCount);
        for (int i = 1; i <= threadCount; i++)
            futures.add(pool.submit(newTestThread(millis(threadCount * 10))));
        for (Future<?> future : futures)
            future.get();
        shutdown(pool).waitingForCompletion(seconds(5));
    }

    private static Callable<Void> newTestThread(final Duration delay) {
        return new Callable<Void>() {
            @Override
            public Void call() throws RuntimeException {
                for (int count = 1; count <= repetitions; count++) {
                    Runnable task = newRunnable();
                    timer.beforeExecute(currentThread(), task);
                    clock.incrementBy(delay);
                    timer.afterExecute(task, NO_EXCEPTION);
                    assertThat(timer.getMeanExecutionTime(), is(delay.inMillis()));
                    Introduce.jitter();
                }
                return null;
            }

            private Runnable newRunnable() {
                return new Runnable() {
                    @Override
                    public void run() {
                    }
                };
            }

        };
    }

}
