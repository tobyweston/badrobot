package bad.robot.pingpong.instrument;

import bad.robot.pingpong.memory.shared.ThreadCounter;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class InstrumentingThreadFactoryTest {

    private final Mockery context = new Mockery();
    private final ThreadCounter statistics = context.mock(ThreadCounter.class);
    private final InstrumentingThreadFactory factory = new InstrumentingThreadFactory(statistics);

    @Test
    public void willDelegateToRunnable() {
        final Runnable runnable = context.mock(Runnable.class);
        context.checking(new Expectations(){{
            one(runnable).run();
            ignoring(statistics);
        }});
        factory.newThread(runnable).run();
    }
    
    @Test
    public void willIncrementActiveThreadCountWhenStarted() {
        context.checking(new Expectations(){{
            one(statistics).incrementActiveThreads();
            ignoring(statistics).decrementActiveThreads();
            ignoring(statistics).incrementThreadCount();
        }});
        factory.newThread(stub()).run();
    }

    @Test
    public void willDecrementActiveThreadCountWhenDone() {
        context.checking(new Expectations(){{
            ignoring(statistics).incrementActiveThreads();
            one(statistics).decrementActiveThreads();
            ignoring(statistics).incrementThreadCount();
        }});
        factory.newThread(stub()).run();
    }

    @Test (expected = RuntimeException.class)
    public void willDecrementActiveThreadCountWhenExceptionOccurs() {
        final Runnable runnable = context.mock(Runnable.class);
        context.checking(new Expectations() {{
            one(runnable).run(); will(throwException(new RuntimeException()));
            ignoring(statistics).incrementActiveThreads();
            one(statistics).decrementActiveThreads();
            ignoring(statistics).incrementThreadCount();
        }});
        factory.newThread(runnable).run();
    }

    @Test
    public void willIncrementThreadCount() {
        context.checking(new Expectations(){{
            never(statistics).incrementActiveThreads();
            never(statistics).decrementActiveThreads();
            one(statistics).incrementThreadCount();
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
