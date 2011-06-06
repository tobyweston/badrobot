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
 * Shows the thread contention based on sampling the current thread's total wait count (which in turn is based
 * on the number of times the thread has been entered the {@link java.lang.Thread.State#WAITING WAITING} or
 * {@link java.lang.Thread.State#TIMED_WAITING TIMED_WAITING} states, divided by the number of samples taken.
 * <p/>
 * It offers only an estimation of the contention ratio but in order to get an accurate ratio as possible,
 * clients should call the {@link #sample()} method consistently along with the cause of any waiting. For example, the
 * ratio produced will include any {@link Thread#sleep(long)} time which will probably not indicate contention. However,
 * if the measured thread never sleeps, and only enters a waiting state because of blocking / waiting for monitor
 * acquisition, the class will offer a reasonable estimation of the contention for that monitor. As much as possible,
 * the client should try to keep the number of calls to {@link #sample()} the same as the number of calls to a wait
 * causing method (that's involved in blocking monitor acquisition).
 */
public class WaitingRatio {

    private final Counter count = new AtomicLongCounter();
    private final Map<Long, Long> waited = new ConcurrentHashMap<Long, Long>();
    private final ThreadMXBean jvm;

    public WaitingRatio(Factory<ThreadMXBean> factory) {
        jvm = factory.create();
        if (jvm.isThreadContentionMonitoringSupported())
            jvm.setThreadContentionMonitoringEnabled(true);
    }

    public void sample() {
        count.increment();
        waited.put(currentThread().getId(), getWaitedCount(currentThread()));
    }

    public Double get() {
        double ratio = 0;
        for (Long waited : this.waited.values())
            ratio += (double) waited / (double) count.get();
        return ratio;
    }

    private long getWaitedCount(Thread thread) {
        return jvm.getThreadInfo(thread.getId()).getWaitedCount();
    }

}
