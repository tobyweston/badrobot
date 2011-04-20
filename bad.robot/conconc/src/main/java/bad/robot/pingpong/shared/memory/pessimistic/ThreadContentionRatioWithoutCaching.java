package bad.robot.pingpong.shared.memory.pessimistic;

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
        System.out.printf("%s / %s, %s %n", blocked, threads.size(), ratio);
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
