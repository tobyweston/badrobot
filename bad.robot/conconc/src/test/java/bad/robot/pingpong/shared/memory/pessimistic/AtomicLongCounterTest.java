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

public class AtomicLongCounterTest {

    private final AtomicLongCounter counter = new AtomicLongCounter();

    @Test
    public void shouldInitialise() {
        assertThat(counter.get(), is(0L));
    }

    @Test
    public void shouldIncrement() {
        counter.increment();
        assertThat(counter.get(), is(1L));
    }

    @Test
    public void shouldDecrement() {
        counter.decrement();
        assertThat(counter.get(), is(-1L));
    }

    @Test
    public void shouldReset() {
        counter.increment();
        counter.reset();
        assertThat(counter.get(), is(0L));
    }

}
