package bad.robot.pingpong.shared.memory.pessimistic;

import com.google.code.tempusfugit.Factory;
import com.google.code.tempusfugit.FactoryException;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

class JmxThreadMxBean implements Factory<ThreadMXBean> {
    @Override
    public ThreadMXBean create() throws FactoryException {
        return ManagementFactory.getThreadMXBean();
    }
}
