package bad.robot.pingpong.instrumentation;

import bad.robot.pingpong.instrumentation.threads.InstrumentingThreadFactory;
import bad.robot.pingpong.shared.memory.ThreadCounter;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class InstrumentingThreadFactoryTest {

    private final Mockery context = new Mockery();
    private final ThreadCounter counter = context.mock(ThreadCounter.class);
    private final InstrumentingThreadFactory factory = new InstrumentingThreadFactory(counter);

    @Test
    public void willDelegateToRunnable() {
        final Runnable runnable = context.mock(Runnable.class);
        context.checking(new Expectations(){{
            one(runnable).run();
            ignoring(counter);
        }});
        factory.newThread(runnable).run();
    }
    
    @Test
    public void willIncrementActiveThreadCountWhenStarted() {
        context.checking(new Expectations(){{
            one(counter).incrementActiveThreads();
            ignoring(counter).decrementActiveThreads();
            ignoring(counter).incrementCreatedThreads();
        }});
        factory.newThread(stub()).run();
    }

    @Test
    public void willDecrementActiveThreadCountWhenDone() {
        context.checking(new Expectations(){{
            ignoring(counter).incrementActiveThreads();
            one(counter).decrementActiveThreads();
            ignoring(counter).incrementCreatedThreads();
        }});
        factory.newThread(stub()).run();
    }

    @Test (expected = RuntimeException.class)
    public void willDecrementActiveThreadCountWhenExceptionOccurs() {
        final Runnable runnable = context.mock(Runnable.class);
        context.checking(new Expectations() {{
            one(runnable).run(); will(throwException(new RuntimeException()));
            ignoring(counter).incrementActiveThreads();
            one(counter).decrementActiveThreads();
            ignoring(counter).incrementCreatedThreads();
        }});
        factory.newThread(runnable).run();
    }

    @Test
    public void willIncrementThreadCount() {
        context.checking(new Expectations(){{
            never(counter).incrementActiveThreads();
            never(counter).decrementActiveThreads();
            one(counter).incrementCreatedThreads();
        }});
        factory.newThread(stub());
    }

    private static Runnable stub() {
        return new Runnable() {
            @Override
            public void run() {
            }
        };
    }
}
