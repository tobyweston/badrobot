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

package bad.robot.pingpong.shared.memory.optimistic;

import bad.robot.pingpong.shared.memory.AccumulatingCounter;
import com.google.code.tempusfugit.temporal.Duration;

public class LongMillisecondCounter implements AccumulatingCounter<Duration> {

    private volatile long count = 0;

    @Override
    public void add(Duration duration) {
        count += duration.inMillis();
    }

    @Override
    public Long get() {
        return count;
    }

    @Override
    public void reset() {
        count = 0;
    }
}
