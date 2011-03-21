package bad.robot.pingpong.server;

import org.junit.After;
import org.junit.Test;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;

import static bad.robot.pingpong.shared.memory.pessimistic.PessimisticThreadCounters.createThreadSafeCounterMaintainingInvariant;
import static bad.robot.pingpong.shared.memory.pessimistic.PessimisticThreadPoolTimers.createThreadSafeThreadPoolTimer;

public class JmxIntegrationTest {

    @Test
    public void canRegisterThreadObserver() throws InstanceNotFoundException, MBeanRegistrationException {
        Jmx.register(createThreadSafeCounterMaintainingInvariant(), "1");
        Jmx.unregister(createThreadSafeCounterMaintainingInvariant(), "1");
    }

    @Test
    public void canRegisterThreadPoolObserver() {
        Jmx.register(createThreadSafeThreadPoolTimer(), "2");
        Jmx.unregister(createThreadSafeThreadPoolTimer(), "2");
    }

    @Test
    public void canRegisterTheSameComponentTwice() {
        Jmx.register(createThreadSafeCounterMaintainingInvariant(), "1");
        Jmx.register(createThreadSafeCounterMaintainingInvariant(), "1");
    }

    @After
    public void unregister() {

    }

}
