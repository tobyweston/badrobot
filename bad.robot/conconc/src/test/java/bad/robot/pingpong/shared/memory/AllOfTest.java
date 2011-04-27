package bad.robot.pingpong.shared.memory;

import com.google.code.tempusfugit.concurrency.Callable;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import static bad.robot.pingpong.shared.memory.AllOf.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(JMock.class)
public class AllOfTest {

    private final Mockery context = new Mockery();
    private final Callable<Void, RuntimeException> callable1 = context.mock(Callable.class, "callable1");
    private final Callable<Void, RuntimeException> callable2 = context.mock(Callable.class, "callable2");
    
    @Test
    public void delegateToCallables() {
        context.checking(new Expectations(){{
            one(callable1).call();
            one(callable2).call();
        }});
        allOf(callable1, callable2).call();
    }

    @Test
    public void shouldWrapException() {
        final Throwable exception = new Exception("hello");
        context.checking(new Expectations(){{
            one(callable1).call(); will(throwException(exception));
            never(callable2).call();
        }});
        try {
            allOf(callable1, callable2).call();
            fail();
        } catch (RuntimeException e) {            
            assertThat(e.getMessage(), containsString("hello"));
            assertThat(e.getCause(), is(exception));
        }
    }
}
