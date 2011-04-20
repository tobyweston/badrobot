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
