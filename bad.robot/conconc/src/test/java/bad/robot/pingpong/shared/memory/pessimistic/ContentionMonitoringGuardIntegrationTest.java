package bad.robot.pingpong.shared.memory.pessimistic;

import bad.robot.pingpong.Introduce;
import bad.robot.pingpong.UpTo;
import bad.robot.pingpong.server.Jmx;
import com.google.code.tempusfugit.concurrency.Callable;
import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import static com.google.code.tempusfugit.concurrency.ThreadUtils.sleep;
import static java.lang.Integer.toHexString;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * It's difficult to simulate contention, you can potentially overload the service with lots of activity (threads and so
 * on) but if the underlying service is very quick, it can be hard to trigger contention. Making the service take longer
 * means there is more chance of collisions/contention. We can't mock the time here as we're relying on real time to
 * cause the contention.
 *
 * We could potentially cause artificial contention like in {@link ThreadMxBeanTest}
 *
 * @see {@link bad.robot.pingpong.shared.memory.optimistic.ContentionMonitoringStmGuardIntegrationTest}
 */
public class ContentionMonitoringGuardIntegrationTest {

    private static final ContentionMonitoringGuard guard = new ContentionMonitoringGuard();

    @BeforeClass
    public static void registerWithJmx() {
        Jmx.register(guard, "Contention-" + guard.getClass().getSimpleName() + "@" + toHexString(guard.hashCode()));
    }

    @Rule public RepeatingRule repeating = new RepeatingRule();
    @Rule public ConcurrentRule concurrent = new ConcurrentRule();

    @Test
    @Concurrent (count = 5)
    @Repeating (repetition = 100)
    public void simulateContention() {
        guard.execute(new Callable<Void, RuntimeException>() {
            @Override
            public Void call() throws RuntimeException {
                sleep(UpTo.millis(5));
                return null;
            }
        });
        Introduce.jitter();
    }

    @AfterClass
    public static void verify() {
        assertThat(guard.getContentionRatio(), is(allOf(lessThan(0.96), greaterThan(0.91))));
    }

}
