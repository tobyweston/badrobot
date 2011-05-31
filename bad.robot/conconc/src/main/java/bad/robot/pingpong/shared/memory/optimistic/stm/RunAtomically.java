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
import com.google.code.tempusfugit.concurrency.Callable;

import static org.multiverse.api.StmUtils.scheduleCompensatingTask;
import static org.multiverse.api.StmUtils.scheduleDeferredTask;

/**
 * The {@link #atomically} methods delegates to the {@link Callable} passed in. It catches {@link Exception} and re-throws
 * as a {@link RuntimeException} to allow the STM implementation to attempt a retry. Serious (non-recoverable/STM implementation
 * events like {@link org.multiverse.api.exceptions.ReadConflict}) extend {@link Error} so as not to be allow this kind
 * of thing to interfere with it.
 *
 * So, we shouldn't catch {@link Throwable} in the try-catch block.
 *
 * @see http://multiverse.codehaus.org/apidocs/org/multiverse/api/exceptions/ControlFlowError.html
 */
public class RunAtomically<R, E extends Exception> extends Atomic<R> {

    private final Callable<R, E> callable;
    private final DeferredTask onCommit;
    private final CompensatingTask onAbort;

    public static <R, E extends Exception> R runAtomically(Callable<R, E> callable) {
        return new RunAtomically<R, E>(callable, new DoNothingDeferredTask(), new DoNothingCompensatingTask()).execute();
    }

    public static <R, E extends Exception> R runAtomically(Callable<R, E> callable, DeferredTask onCommit) {
        return new RunAtomically<R, E>(callable, onCommit, new DoNothingCompensatingTask()).execute();
    }

    public static <R, E extends Exception> R runAtomically(Callable<R, E> callable, CompensatingTask onAbort) {
        return new RunAtomically<R, E>(callable, new DoNothingDeferredTask(), onAbort).execute();
    }

    public static <R, E extends Exception> R runAtomically(Callable<R, E> callable, DeferredTask onCommit, CompensatingTask onAbort) {
        return new RunAtomically<R, E>(callable, onCommit, onAbort).execute();
    }

    RunAtomically(Callable<R, E> callable, DeferredTask onCommit, CompensatingTask onAbort) {
        this.callable = callable;
        this.onCommit = onCommit;
        this.onAbort = onAbort;
    }

    @Override
    public R atomically() {
        try {
            scheduleDeferredTask(onCommit);
            scheduleCompensatingTask(onAbort);
            return callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
