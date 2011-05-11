/*
 * Copyright (c) 2009-2011, bad robot (london) ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

import static bad.robot.pingpong.shared.memory.optimistic.OptimisticThroughput.createThreadUnsafeThroughput;
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

    private static final ContentionMonitoringGuard guard = new ContentionMonitoringGuard(createThreadUnsafeThroughput());

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
        Double ratio = guard.getContentionRatio();
        System.out.println(ratio);
        assertThat(ratio, is(allOf(lessThan(0.96), greaterThan(0.91))));
    }

}
