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

import bad.robot.pingpong.shared.memory.Counter;
import com.google.code.tempusfugit.Factory;

import java.lang.management.ThreadMXBean;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.Thread.currentThread;

public class ThreadContentionRatio {

    private final Counter count = new AtomicLongCounter();
    private final Map<Long, Long> blocked = new ConcurrentHashMap<Long, Long>();
    private final ThreadMXBean jvm;

    public ThreadContentionRatio(Factory<ThreadMXBean> factory) {
        jvm = factory.create();
        if (jvm.isThreadContentionMonitoringSupported())
            jvm.setThreadContentionMonitoringEnabled(true);
    }

    public void sample() {
        count.increment();
        blocked.put(currentThread().getId(), getBlockedCount(currentThread()));
    }

    public Double get() {
        double ratio = 0;
        for (Long blocked : this.blocked.values())
            ratio += (double) blocked / (double) count.get();
        return ratio;
    }

    private long getBlockedCount(Thread thread) {
        return jvm.getThreadInfo(thread.getId()).getBlockedCount();
    }

}
