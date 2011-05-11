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

public class Accumulate<T> implements Callable<Void, RuntimeException> {

    private final T value;
    private final AccumulatingCounter<T> counter;

    public static <T> Accumulate<T> add(T value, To<T> counter) {
        return new Accumulate<T>(value, counter.value);
    }

    public Accumulate(T value, AccumulatingCounter<T> counter) {
        this.value = value;
        this.counter = counter;
    }

    @Override
    public Void call() throws RuntimeException {
        counter.add(value);
        return null;
    }

    public static class To<T> {

        private final AccumulatingCounter<T> value;

        public static <T> To<T> to(AccumulatingCounter<T> counter) {
            return new To<T>(counter);
        }

        private To(AccumulatingCounter<T> value) {
            this.value = value;
        }
    }

}
