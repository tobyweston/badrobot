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

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import static java.lang.Thread.currentThread;

public class ThreadContentionRatioWithoutCaching {

    private final Set<Long> threads = new ConcurrentSkipListSet<Long>();
    private final ThreadMXBean jvm = ManagementFactory.getThreadMXBean();

    public ThreadContentionRatioWithoutCaching() {
        if (jvm.isThreadContentionMonitoringSupported())
            jvm.setThreadContentionMonitoringEnabled(true);
    }

    public void increment() {
        threads.add(currentThread().getId());
    }

    public Double get() {
        double blocked = 0;
        for (Long id : threads)
            blocked += getBlockCount(id);
        double ratio = blocked / (double) threads.size();
//        System.out.printf("%s / %s, %s %n", blocked, threads.size(), ratio);
        return ratio;
    }

    private long getBlockCount(Long threadId) {
        ThreadInfo thread = jvm.getThreadInfo(threadId);
        if (noLongerAvailable(thread))
            return 0;
        return thread.getBlockedCount();
    }

    private static boolean noLongerAvailable(ThreadInfo thread) {
        return thread == null;
    }

}
