package bad.robot.pingpong.shared.memory.pessimistic;

import bad.robot.pingpong.shared.memory.ThreadCounter;
import com.google.code.tempusfugit.concurrency.Callable;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class ThreadCounterInvariantTest {

    private final Mockery context = new Mockery();
    private final Guard guard = context.mock(Guard.class);
    private final ThreadCounter counter = new ThreadCounter(guard, new AtomicLongCounter(), new AtomicLongCounter());

    @Test
    public void shouldMakeGuardedCallOnWrites() throws Exception {
        context.checking(new Expectations() {{
            exactly(3).of(guard).execute(with(any(Callable.class)));
        }});
        counter.threadCreated();
        counter.threadStarted();
        counter.threadTerminated();
    }

    @Test
    public void shouldMakeGuardedCallForResetAndSoMaintainInvariant() throws Exception {
        context.checking(new Expectations() {{
            one(guard).guarding(); will(returnValue(true));
            one(guard).execute(with(any(Callable.class)));
        }});
        counter.reset();
    }

    @Test
    public void shouldNotAttemptGuardedCall() throws Exception {
        context.checking(new Expectations() {{
            one(guard).guarding(); will(returnValue(false));
            never(guard).execute(with(any(Callable.class)));
        }});
        counter.reset();
    }

}
