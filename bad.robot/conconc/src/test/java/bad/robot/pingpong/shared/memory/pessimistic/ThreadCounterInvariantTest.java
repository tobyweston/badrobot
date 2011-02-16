package bad.robot.pingpong.shared.memory.pessimistic;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@RunWith(JMock.class)
public class ThreadCounterInvariantTest {

    private final Mockery context = new Mockery();
    private final Lock lock = context.mock(Lock.class);
    private final ThreadCounter counter = new ThreadCounter(new LockingGuard(lock), new AtomicLongCounter(), new AtomicLongCounter());

    @Test
    public void shouldLockOnWrites() throws Exception {
        context.checking(new Expectations() {{
            exactly(3).of(lock).lock();
            exactly(3).of(lock).unlock();
        }});
        counter.threadCreated();
        counter.threadStarted();
        counter.threadTerminated();
    }

    @Test
    public void shouldLockForResetAndSoMaintainInvariant() throws InterruptedException {
        context.checking(new Expectations() {{
            one(lock).tryLock(with(any(Long.class)), with(any(TimeUnit.class)));
            will(returnValue(true));
            one(lock).lock();
            one(lock).unlock();
        }});
        counter.reset();
    }

}
