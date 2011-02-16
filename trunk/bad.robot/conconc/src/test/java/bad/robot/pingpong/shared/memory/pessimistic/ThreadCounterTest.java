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
import bad.robot.pingpong.shared.memory.CounterFactory;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static bad.robot.pingpong.shared.memory.pessimistic.Unguarded.unguarded;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(JMock.class)
public class ThreadCounterTest {

    private final Mockery context = new Mockery();
    private final CounterFactory factory = context.mock(CounterFactory.class, "factory");
    private final Counter activeThreads = context.mock(Counter.class, "active");
    private final Counter createdThreads = context.mock(Counter.class, "created");

    private ThreadCounter counter;

    @Before
    public void setupCounter() {
        context.checking(new Expectations(){{
            one(factory).create(); will(returnValue(activeThreads));
            one(factory).create(); will(returnValue(createdThreads));
        }});
        counter = new ThreadCounter(unguarded(), factory);
    }

    @Test
    public void shouldGetActiveThreads() {
        context.checking(new Expectations(){{
            one(activeThreads).get(); will(returnValue(1L));
        }});
        assertThat(counter.getActiveCount(), is(1L));
    }

    @Test
    public void shouldGetCreatedThreads() {
        context.checking(new Expectations(){{
            one(createdThreads).get(); will(returnValue(2L));
        }});
        assertThat(counter.getCreatedCount(), is(2L));
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
