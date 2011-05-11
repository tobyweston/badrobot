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
import com.google.code.tempusfugit.temporal.Duration;

public class Throughput implements RequestObserver, ThroughputMBean {

    private final StopWatch timer;
    private final Counter count;
    private final AccumulatingCounter<Duration> totalTime;

    public Throughput(StopWatch timer, Counter count, AccumulatingCounter<Duration> totalTime) {
        this.timer = timer;
        this.count = count;
        this.totalTime = totalTime;
    }

    @Override
    public Request started() {
        timer.start();
        return new Request() {
            @Override
            public Duration finished() {
                count.increment();
                timer.stop();
                totalTime.add(timer.elapsedTime());
                return timer.elapsedTime();
            }
        };
    }

    @Override
    public Double getRequestsPerSecond() {
        return (double) count.get() / ((double) totalTime.get() / 1000);
    }

    @Override
    public Long getTotalRequests() {
        return count.get();
    }
}
