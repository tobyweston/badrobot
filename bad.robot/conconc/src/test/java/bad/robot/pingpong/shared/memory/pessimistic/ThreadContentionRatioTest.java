package bad.robot.pingpong.shared.memory.pessimistic;

import com.google.code.tempusfugit.Factory;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(JMock.class)
public class ThreadContentionRatioTest {

    private final Mockery context = new Mockery() {{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};
    private final Factory<ThreadMXBean> factory = context.mock(Factory.class);
    private final ThreadMXBean jvm = context.mock(ThreadMXBean.class);
    private final ThreadInfo thread = context.mock(ThreadInfo.class);

    @Test
    public void constructionSetsUpContentionMonitoring() throws Exception {
        expectingInitialisation();
        ThreadContentionRatio ratio = new ThreadContentionRatio(factory);
        assertThat(ratio.get(), is(0.0));
    }

    @Test
    public void shouldCalculateOnHighContention() {
        expectingInitialisation();
        ThreadContentionRatio ratio = new ThreadContentionRatio(factory);
        expectingContention(5, 5L);
        ratio.sample();
        ratio.sample();
        ratio.sample();
        ratio.sample();
        ratio.sample();
        assertThat(ratio.get(), is(1.0));
    }

    @Test
    public void shouldCalculateLowNoContention() {
        expectingInitialisation();
        ThreadContentionRatio ratio = new ThreadContentionRatio(factory);
        expectingContention(5, 1L);
        ratio.sample();
        ratio.sample();
        ratio.sample();
        ratio.sample();
        ratio.sample();
        assertThat(ratio.get(), is(0.2));
    }

    @Test
    public void shouldCalculateOnNoContention() {
        expectingInitialisation();
        ThreadContentionRatio ratio = new ThreadContentionRatio(factory);
        expectingContention(2, 0L);
        ratio.sample();
        ratio.sample();
        assertThat(ratio.get(), is(0.0));
    }

    private void expectingContention(final int requests, final long blockCount) {
        context.checking(new Expectations(){{
            allowing(jvm).getThreadInfo(with(any(Long.class))); will(returnValue(thread));
            exactly(requests).of(thread).getBlockedCount(); will(returnValue(blockCount));
            ignoring(thread);
        }});
    }

    private void expectingInitialisation() {
        context.checking(new Expectations(){{
            one(factory).create(); will(returnValue(jvm));
            one(jvm).isThreadContentionMonitoringSupported(); will(returnValue(true));
            one(jvm).setThreadContentionMonitoringEnabled(true);
        }});
    }

}
