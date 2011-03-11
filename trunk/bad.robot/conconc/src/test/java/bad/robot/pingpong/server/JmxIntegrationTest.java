package bad.robot.pingpong.server;

import bad.robot.pingpong.shared.memory.ThreadPoolTimer;
import org.junit.After;
import org.junit.Test;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;

import static bad.robot.pingpong.shared.memory.pessimistic.PessimisticThreadCounters.createLockBasedThreadSafeCounter;

public class JmxIntegrationTest {

    @Test
    public void canRegisterThreadObserver() throws InstanceNotFoundException, MBeanRegistrationException {
        Jmx.register(createLockBasedThreadSafeCounter(), "1");
        Jmx.unregister(createLockBasedThreadSafeCounter(), "1");
    }

    @Test
    public void canRegisterThreadPoolObserver() {
        Jmx.register(new ThreadPoolTimer(), "2");
        Jmx.unregister(new ThreadPoolTimer(), "2");
    }

    @Test
    public void canRegisterTheSameComponentTwice() {
        Jmx.register(createLockBasedThreadSafeCounter(), "1");
        Jmx.register(createLockBasedThreadSafeCounter(), "1");
    }

    @After
    public void unregister() {

    }

}
