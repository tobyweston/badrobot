package bad.robot.pingpong.shared.memory.optimistic;

import com.google.code.tempusfugit.concurrency.Callable;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import static bad.robot.pingpong.shared.memory.optimistic.RunAtomically.runAtomically;

@RunWith(JMock.class)
public class RunAtomicallyTest {

    private final Mockery context = new Mockery();
    private final Callable callable = context.mock(Callable.class);

    @Test
    public void shouldDelegate() throws Exception {
        context.checking(new Expectations(){{
            one(callable).call();
        }});
        runAtomically(callable);
    }

    @Test
    public void shouldDelegateOnAtomically() throws Exception {
        context.checking(new Expectations(){{
            one(callable).call();
        }});
        new RunAtomically(callable).atomically();
    }

    @Test(expected = RuntimeException.class)
    public void shouldWrapException() throws Exception {
        context.checking(new Expectations(){{
            one(callable).call(); will(throwException(new Exception()));
        }});
        runAtomically(callable);
    }

    @Test(expected = Error.class)
    public void shouldRethrowError() throws Exception {
        context.checking(new Expectations(){{
            one(callable).call(); will(throwException(new Error()));
        }});
        runAtomically(callable);
    }
}
