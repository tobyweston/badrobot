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

package bad.robot.pingpong.shared.memory.pessimistic.synchronised;

import bad.robot.pingpong.shared.memory.Guard;
import bad.robot.pingpong.shared.memory.RequestObserver;
import bad.robot.pingpong.shared.memory.Throughput;
import bad.robot.pingpong.shared.memory.pessimistic.JmxThreadMxBean;
import com.google.code.tempusfugit.concurrency.Callable;

public class ContentionMonitoringGuard implements Guard, ContentionMonitoringGuardMBean {

    private final ThreadContentionRatio contention = new ThreadContentionRatio(new JmxThreadMxBean());
    private final Throughput throughput;

    public ContentionMonitoringGuard(Throughput throughput) {
        this.throughput = throughput;
    }

    @Override
    public <R, E extends Exception> R execute(Callable<R, E> callable) throws E {
        synchronized (this) {
            RequestObserver.Request request = throughput.started();
            try {
                return callable.call();
            } finally {
                request.finished();
                contention.sample();
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
