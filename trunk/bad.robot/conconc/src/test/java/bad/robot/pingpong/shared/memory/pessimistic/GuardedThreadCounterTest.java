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

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GuardedThreadCounterTest {

    private final GuardedThreadCounter counter = new GuardedThreadCounter();

    @Test
    public void shouldInitialiseThreadCounts() {
        assertThat(counter.getActiveThreads(), is(0L));
    }

    @Test
    public void shouldIncrementActiveThreadCount() {
        incrementActiveThreads(by(3));
        assertThat(counter.getActiveThreads(), is(3L));
    }

    @Test
    public void shouldIncrementThreadCount() {
        incrementThreads(by(4));
        assertThat(counter.getThreadCount(), is(4L));
    }

    @Test
    public void shouldDecrementActiveThreadCount() {
        incrementActiveThreads(by(5));
        assertThat(counter.getActiveThreads(), is(5L));
        decrementActiveThreads(by(5));
        assertThat(counter.getActiveThreads(), is(0L));
    }

    @Test
    public void shouldResetCounts() {
        incrementActiveThreads(by(8));
        incrementThreads(by(5));
        counter.reset();
        assertThat(counter.getActiveThreads(), is(0L));
        assertThat(counter.getThreadCount(), is(0L));
    }

    private void incrementActiveThreads(int by) {
        for (int i = 0; i < by; i++)
            counter.incrementActiveThreads();
    }

    private void decrementActiveThreads(int by)  {
        for (int i = 0; i < by; i++)
            counter.decrementActiveThreads();
    }

    private void incrementThreads(int by)  {
        for (int i = 0; i < by; i++)
            counter.incrementCreatedThreads();
    }

    private static int by(int count) {
        return count;
    }

}
