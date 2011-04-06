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

import com.google.code.tempusfugit.concurrency.Callable;


public class Divide implements Callable<Long, RuntimeException> {

    private AccumulatingCounter<?> dividend;
    private Counter divisor;

    public static Divide divide(AccumulatingCounter<?> dividend, Divisor divisor) {
        return new Divide(dividend, divisor);
    }

    private Divide(AccumulatingCounter<?> dividend, Divisor divisor) {
        this.dividend = dividend;
        this.divisor = divisor.value;
    }

    @Override
    public Long call() throws RuntimeException {
        return dividend.get() / divisor.get();
    }

    public static class Divisor {
        private Counter value;

        public static Divisor by(Counter counter) {
            return new Divisor(counter);
        }

        private Divisor(Counter value) {
            this.value = value;
        }
    }
}
