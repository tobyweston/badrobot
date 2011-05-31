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

import akka.stm.Atomic;
import akka.stm.Ref;
import bad.robot.pingpong.shared.memory.Counter;
import com.google.code.tempusfugit.concurrency.annotations.ThreadSafe;

@ThreadSafe
public class StmAtomicLongCounter implements Counter {

    private final Ref<Long> count = new Ref<Long>(0L);

    @Override
    public void increment() {
        new Atomic<Long>() {
            @Override
            public Long atomically() {
                return count.set(count.get() + 1L);
            }
        }.execute();
    }

    @Override
    public void decrement() {
        new Atomic<Long>() {
            @Override
            public Long atomically() {
                return count.set(count.get() - 1L);
            }
        }.execute();
    }

    @Override
    public Long get() {
        return count.get();
    }

    @Override
    public void reset() {
        new Atomic<Long>() {
            @Override
            public Long atomically() {
                return count.set(0L);
            }
        }.execute();
    }
}
