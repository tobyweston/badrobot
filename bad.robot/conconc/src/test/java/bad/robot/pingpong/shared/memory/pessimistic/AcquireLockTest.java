package bad.robot.pingpong.shared.memory.pessimistic;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import static java.lang.Thread.currentThread;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(JMock.class)
public class AcquireLockTest {

    private final Mockery context = new Mockery();
    private final Lock lock = context.mock(Lock.class);

    @Test
    public void shouldAcquireLock() throws InterruptedException {
        context.checking(new Expectations(){{
            one(lock).tryLock(with(any(Long.class)), with(any(TimeUnit.class))); will(returnValue(true));
        }});
        assertThat(AcquireLock.acquired(lock), is(true));
    }

    @Test
    public void interruptFlagIsResetOnInterruption() throws InterruptedException {
        context.checking(new Expectations(){{
            one(lock).tryLock(with(any(Long.class)), with(any(TimeUnit.class))); will(throwException(new InterruptedException()));
        }});
        assertThat(currentThread().isInterrupted(), is(false));
        AcquireLock.acquired(lock);
        assertThat(currentThread().isInterrupted(), is(true));
    }
}
