package bad.robot.pingpong.shared.memory.optimistic;

import akka.stm.Ref;
import bad.robot.pingpong.Introduce;
import bad.robot.pingpong.UpTo;
import com.google.code.tempusfugit.concurrency.Callable;
import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;

import static com.google.code.tempusfugit.concurrency.ThreadUtils.sleep;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @see {@link bad.robot.pingpong.shared.memory.pessimistic.ContentionMonitoringGuardIntegrationTest}
 */
public class ContentionMonitoringStmGuardIntegrationTest {

    private static final ContentionMonitoringStmGuard guard = new ContentionMonitoringStmGuard();
    private static final Ref<Long> shared = new Ref<Long>(0L);

    @Rule public RepeatingRule repeating = new RepeatingRule();
    @Rule public ConcurrentRule concurrent = new ConcurrentRule();


    @Test
    @Concurrent (count = 5)
    @Repeating (repetition = 100)
    public void simulateContention() {
        guard.execute(new Callable<Void, RuntimeException>() {
            @Override
            public Void call() throws RuntimeException {
                shared.set(666L);
                sleep(UpTo.millis(5));
                return null;
            }
        });
        Introduce.jitter();
    }

    @AfterClass
    public static void verify() {
        assertThat(guard.getContentionRatio(), is(allOf(lessThan(1.80), greaterThan(1.60))));
    }
}
