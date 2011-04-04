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

package bad.robot.pingpong.shared.memory;

import bad.robot.pingpong.Introduce;
import com.google.code.tempusfugit.concurrency.ConcurrentRule;
import com.google.code.tempusfugit.concurrency.ConcurrentTestRunner;
import com.google.code.tempusfugit.concurrency.RepeatingRule;
import com.google.code.tempusfugit.concurrency.annotations.Concurrent;
import com.google.code.tempusfugit.concurrency.annotations.Repeating;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(ConcurrentTestRunner.class)
public abstract class AbstractThreadCounterIntegrationTest {

    protected static ThreadCounter counter;

    @Rule public ConcurrentRule concurrent = new ConcurrentRule();
    @Rule public RepeatingRule repeating = new RepeatingRule();

    @Test
    @Repeating
    @Concurrent(count = 50)
    public void notifyThreadStarted() {
        counter.threadStarted();
        Introduce.jitter();
    }

    @Test
    @Repeating
    @Concurrent(count = 10)
    public void notifyThreadTerminated() {
        counter.threadTerminated();
        Introduce.jitter();
    }

    @Test
    @Repeating
    @Concurrent(count = 50)
    public void notifyThreadCreated() {
        counter.threadCreated();
        Introduce.jitter();
    }

    @AfterClass
    public static void verifyCounter() {
        assertThat(counter.getActiveThreads(), is(4000L));
        assertThat(counter.getCreatedThreads(), is(5000L));
    }
}
