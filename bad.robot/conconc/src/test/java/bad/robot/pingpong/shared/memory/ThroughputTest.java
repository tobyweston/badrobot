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

import bad.robot.pingpong.shared.memory.optimistic.LongCounter;
import bad.robot.pingpong.shared.memory.optimistic.LongMillisecondCounter;
import com.google.code.tempusfugit.temporal.Duration;
import org.junit.Test;

import static com.google.code.tempusfugit.temporal.Duration.millis;
import static java.lang.Double.NaN;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ThroughputTest {

    private final StopWatchStub timer = new StopWatchStub();
    private final Throughput throughput = new Throughput(timer, new LongCounter(), new LongMillisecondCounter());

    @Test
    public void calculateThroughputWithNoRequests() {
        throughput.started();
        timer.setElapsedTime(millis(355));
        assertThat(throughput.getRequestsPerSecond(), is(NaN));
    }

    @Test
    public void calculateThroughput() {
        makeRequestLasting(millis(250));
        makeRequestLasting(millis(150));
        makeRequestLasting(millis(50));
        makeRequestLasting(millis(300));
        assertThat(throughput.getTotalRequests(), is(4L));
        assertThat(throughput.getRequestsPerSecond(), is(5.333333333333333));
    }

    private void makeRequestLasting(Duration duration) {
        RequestObserver.Request request = throughput.started();
        timer.setElapsedTime(duration);
        request.finished();
    }
}
