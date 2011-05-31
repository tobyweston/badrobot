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

package bad.robot.pingpong.shared.memory.optimistic.stm;

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
 * @see {@link bad.robot.pingpong.shared.memory.pessimistic.synchronised.ContentionMonitoringGuardIntegrationTest}
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
