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

import bad.robot.pingpong.StopWatch;
import bad.robot.pingpong.shared.memory.optimistic.atomic.AtomicLongCounter;
import bad.robot.pingpong.shared.memory.optimistic.atomic.AtomicMillisecondCounter;
import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;

import static com.google.code.tempusfugit.temporal.Duration.millis;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ThroughputIntegrationTest {

    private static final ThreadLocalMovableClock clock = new ThreadLocalMovableClock();
    private static final StopWatch timer = new ThreadLocalStopWatch(clock);
    private static final Throughput throughput = new Throughput(timer, new AtomicLongCounter(), new AtomicMillisecondCounter());

    @Rule public ConcurrentRule concurrent = new ConcurrentRule();
    @Rule public RepeatingRule repeating = new RepeatingRule();

    @Concurrent (count = 10)
    @Repeating (repetition = 100)
    @Test
    public void recordThroughput() {
        RequestObserver.Request request = throughput.started();
        clock.incrementBy(millis(250));
        request.finished();
    }
    
    @AfterClass
    public static void verify() {
        assertThat(throughput.getTotalRequests(), is(1000L));
        assertThat(throughput.getRequestsPerSecond(), is(4.0D));
    }
}
