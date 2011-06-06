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

import bad.robot.pingpong.shared.memory.Counter;
import bad.robot.pingpong.shared.memory.optimistic.atomic.AtomicLongCounter;
import com.google.code.tempusfugit.Factory;

import java.lang.management.ThreadMXBean;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.Thread.currentThread;

/**
 * Shows the thread contention based on sampling the current thread's blocked count (which in turn is based
 * on the number of times the thread has been entered the {@link java.lang.Thread.State#BLOCKED BLOCKED} state,
 * by a call to <code>synchronized</code>) divided by the number of samples taken.
 *
 * It offers only an estimation of the contention ratio but in order to get an accurate ratio as possible,
 * clients should call the {@link #sample()} method consistently along with call that are synchronized. For example,
 * sampling after every call to a synchronized section.
 */
public class BlockingRatio {

    private final Counter count = new AtomicLongCounter();
    private final Map<Long, Long> blocked = new ConcurrentHashMap<Long, Long>();
    private final ThreadMXBean jvm;

    public BlockingRatio(Factory<ThreadMXBean> factory) {
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
