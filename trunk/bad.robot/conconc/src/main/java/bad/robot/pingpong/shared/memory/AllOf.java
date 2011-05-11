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

import static com.google.code.tempusfugit.ExceptionWrapper.wrapAsRuntimeException;

public class AllOf implements Callable<Void, RuntimeException> {

    private final Callable<?, ?>[] callables;

    public static AllOf allOf(Callable<?, ?>... callables) {
        return new AllOf(callables);
    }

    private AllOf(Callable<?, ?>... callables) {
        this.callables = callables;
    }

    @Override
    public Void call() throws RuntimeException {
        for (Callable<?, ?> callable : callables)
            wrapAsRuntimeException(callable);
        return null;
    }
}
