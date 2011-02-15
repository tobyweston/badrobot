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
import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.ConcurrentTestRunner;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static bad.robot.pingpong.UpTo.upTo;
import static com.google.code.tempusfugit.temporal.Duration.millis;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(ConcurrentTestRunner.class)
@Ignore
public class ThreadCounterInvariantIntegrationTest {

    private static final ThreadCounter counter = new ThreadCounter();

    @Rule public ConcurrentRule concurrent = new ConcurrentRule();
    @Rule public RepeatingRule repeating = new RepeatingRule();

    @Test
    @Repeating
    @Concurrent
    public void notifyThreadStarted() {
        counter.threadStarted();
        Introduce.jitter();
    }

    @Test
    @Repeating
    @Concurrent
    public void notifyThreadCreated() {
        counter.threadCreated();
        Introduce.jitter();
    }

    @Test
    public void resetCounters() {
        Introduce.jitter(upTo(millis(100)));
        counter.reset();
    }

    @AfterClass
    public static void verifyActiveAndTotalCountsAreResetAtomically() {
        String message = "total and active thread counts should be the same, otherwise a race condition exists";
        assertThat(message, counter.getCreatedCount(), is(counter.getActiveCount()));
    }


}
