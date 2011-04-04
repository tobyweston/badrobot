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
import bad.robot.pingpong.StopWatch;
import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicLong;

import static com.google.code.tempusfugit.temporal.Duration.millis;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ThreadLocalStopWatchIntegrationTest {

    private final static ThreadLocalMovableClock clock = new ThreadLocalMovableClock();
    private final static StopWatch timer = new ThreadLocalStopWatch(clock);

    private final static AtomicLong totalTime = new AtomicLong(0L);

    @Rule public RepeatingRule repeating = new RepeatingRule();
    @Rule public ConcurrentRule concurrent = new ConcurrentRule();

    @Test
    @Concurrent(count = 10)
    @Repeating(repetition = 50)
    public void elapsedTimeWhenUsingThreadLocalMovableClock() throws InterruptedException {
        timer.start();
        clock.incrementBy(millis(100));
        timer.stop();
        totalTime.getAndAdd(timer.elapsedTime().inMillis());
        Introduce.jitter();
    }

    @AfterClass
    public static void verify() {
        long expected = 100 * 50 * 10;
        assertThat(totalTime.get(), is(expected));
    }
}
