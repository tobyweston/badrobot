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

package bad.robot.pingpong.shared.memory.optimistic.atomic;

import bad.robot.pingpong.shared.memory.Counter;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongCounter implements Counter {

    private final AtomicLong count = new AtomicLong();

    @Override
    public void increment() {
        count.getAndIncrement();
    }

    @Override
    public void decrement() {
        count.getAndDecrement();
    }

    @Override
    public Long get() {
        return count.get();
    }

    @Override
    public void reset() {
        count.set(0);
    }
}
