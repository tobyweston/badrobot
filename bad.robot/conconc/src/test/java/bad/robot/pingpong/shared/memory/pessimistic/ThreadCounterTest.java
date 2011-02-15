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

public class ThreadCounterTest {

    private final ThreadCounter counter = new ThreadCounter(new Unguarded());

    @Test
    public void shouldInitialiseCounts() {
        assertThat(counter.getActiveCount(), is(0L));
        assertThat(counter.getCreatedCount(), is(0L));
    }

    @Test
    public void shouldIncrementActiveCount() {
        incrementActiveThreadsBy(3);
        assertThat(counter.getActiveCount(), is(3L));
    }

    @Test
    public void shouldDecrementActiveThreadCount() {
        incrementActiveThreadsBy(5);
        assertThat(counter.getActiveCount(), is(5L));
        decrementActiveThreadsBy(5);
        assertThat(counter.getActiveCount(), is(0L));
    }

    @Test
    public void shouldIncrementCreatedCount() {
        incrementThreadsBy(6);
        assertThat(counter.getCreatedCount(), is(6L));
    }

    @Test
    public void shouldResetCounts() {
        incrementActiveThreadsBy(8);
        incrementThreadsBy(5);
        counter.reset();
        assertThat(counter.getActiveCount(), is(0L));
        assertThat(counter.getCreatedCount(), is(0L));
    }

    private void incrementActiveThreadsBy(int by) {
        for (int i = 0; i < by; i++)
            counter.threadStarted();
    }

    private void decrementActiveThreadsBy(int by)  {
        for (int i = 0; i < by; i++)
            counter.threadTerminated();
    }

    private void incrementThreadsBy(int by)  {
        for (int i = 0; i < by; i++)
            counter.threadCreated();
    }

}
