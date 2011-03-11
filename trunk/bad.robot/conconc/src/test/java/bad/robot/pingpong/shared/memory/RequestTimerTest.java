package bad.robot.pingpong.shared.memory;

import bad.robot.pingpong.StopWatch;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.code.tempusfugit.temporal.Duration.millis;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(JMock.class)
public class RequestTimerTest {

    private final Mockery context = new Mockery();
    private final StopWatch stopWatch = context.mock(StopWatch.class);
    private final RequestTimer timer = new RequestTimer(stopWatch);

    @Test
    public void shouldTimeRequest() {
        context.checking(new Expectations(){{
            one(stopWatch).start(); 
            one(stopWatch).stop();
            one(stopWatch).elapsedTime(); will(returnValue(millis(606)));
        }});
        RequestObserver.Request request = timer.started();
        assertThat(request.finished(), is(millis(606)));
    }
}
