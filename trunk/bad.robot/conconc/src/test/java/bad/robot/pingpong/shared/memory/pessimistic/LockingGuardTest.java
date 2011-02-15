package bad.robot.pingpong.shared.memory.pessimistic;

import com.google.code.tempusfugit.concurrency.Callable;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@RunWith(JMock.class)
public class LockingGuardTest {

    private final Mockery context = new JUnit4Mockery();
    
    private final Lock lock = context.mock(Lock.class);

    @Test
    public void locksAndUnlocks() {
        setExpectationsOn(lock);
        new LockingGuard(lock).execute(something());
    }

    @Test(expected = Exception.class)
    public void locksAndUnlocksWhenExceptionThrown() throws Exception {
        setExpectationsOn(lock);
        new LockingGuard(lock).execute(somethingThatThrowsException());
    }

    @Test
    public void shouldAttemptToAcquireLock() throws InterruptedException {
        context.checking(new Expectations(){{
            one(lock).tryLock(with(any(Long.class)), with(any(TimeUnit.class)));
        }});
        new LockingGuard(lock).guarding();
    }

    private Callable<Void, RuntimeException> something() {
        return new Callable<Void, RuntimeException>() {
            public Void call() throws RuntimeException {
                return null;
            }
        };
    }

    private Callable<Void, Exception> somethingThatThrowsException() {
        return new Callable<Void, Exception>() {
            public Void call() throws Exception {
                throw new RuntimeException("go go go");
            }
        };
    }

    private void setExpectationsOn(final Lock lock) {
        context.checking(new Expectations() {{
            one(lock).lock();
            one(lock).unlock();
        }});
    }

}
