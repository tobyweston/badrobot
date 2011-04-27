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

import bad.robot.pingpong.shared.memory.RealClock;
import bad.robot.pingpong.shared.memory.RequestObserver;
import bad.robot.pingpong.shared.memory.ThreadLocalStopWatch;
import bad.robot.pingpong.shared.memory.Throughput;
import com.google.code.tempusfugit.concurrency.Callable;

public class ContentionMonitoringGuard implements Guard, ContentionMonitoringGuardMBean {

    private final ThreadContentionRatio contention = new ThreadContentionRatio(new JmxThreadMxBean());
    private final Throughput throughput = new Throughput(new ThreadLocalStopWatch(new RealClock()));

    @Override
    public <R, E extends Exception> R execute(Callable<R, E> callable) throws E {
        contention.sample();
        synchronized (this) {
            RequestObserver.Request request = throughput.started();
            try {
                return callable.call();
            } finally {
                request.finished();
            }
        }
    }

    @Override
    public Boolean guarding() {
        return true;
    }

    @Override
    public Double getContentionRatio() {
        return contention.get();
    }

    @Override
    public Double getRequestsPerSecond() {
        return throughput.getRequestsPerSecond();
    }

    @Override
    public Long getTotalRequests() {
        return throughput.getTotalRequests();
    }

}
