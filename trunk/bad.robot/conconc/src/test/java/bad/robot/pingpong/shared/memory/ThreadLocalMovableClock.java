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

import com.google.code.tempusfugit.temporal.Duration;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadLocalMovableClock implements Clock {

    private final ThreadLocal<Date> now;
    private final static AtomicInteger count = new AtomicInteger();

    public ThreadLocalMovableClock() {
        now = new ThreadLocal<Date>() {
            @Override
            protected Date initialValue() {
                count.getAndIncrement();
                return new Date(0);
            }
        };
    }

    @Override
    public Date time() {
        Date date = now.get();
        return new Date(date.getTime());
    }

    public void incrementBy(Duration time) {
        now.get().setTime(now.get().getTime() + time.inMillis());
    }

}
