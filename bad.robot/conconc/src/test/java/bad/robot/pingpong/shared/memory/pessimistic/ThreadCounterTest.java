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

import bad.robot.pingpong.shared.memory.Counter;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static bad.robot.pingpong.shared.memory.pessimistic.Unguarded.unguarded;

@RunWith(JMock.class)
public class ThreadCounterTest {

    private final Mockery context = new Mockery();
    private final Counter activeThreads = context.mock(Counter.class, "active");
    private final Counter createdThreads = context.mock(Counter.class, "created");

    private ThreadCounter counter;

    @Before
    public void setupCounter() {
        counter = new ThreadCounter(unguarded(), activeThreads, createdThreads);
    }

    @Test
    public void shouldIncrementActiveCount() {
        context.checking(new Expectations() {{
            one(activeThreads).increment();
        }});
        counter.threadStarted();
    }

    @Test
    public void shouldDecrementActiveThreadCount() {
        context.checking(new Expectations() {{
            one(activeThreads).decrement();
        }});
        counter.threadTerminated();
    }

    @Test
    public void shouldIncrementCreatedCount() {
        context.checking(new Expectations(){{
            one(createdThreads).increment();
        }});
        counter.threadCreated();
    }

    @Test
    public void shouldResetCounts() {
        context.checking(new Expectations(){{
            one(activeThreads).reset();
            one(createdThreads).reset();
        }});
        counter.reset();
    }
}
