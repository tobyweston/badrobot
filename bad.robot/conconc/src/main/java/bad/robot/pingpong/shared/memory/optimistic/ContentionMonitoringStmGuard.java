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

import bad.robot.pingpong.shared.memory.Counter;
import bad.robot.pingpong.shared.memory.pessimistic.AtomicLongCounter;
import bad.robot.pingpong.shared.memory.pessimistic.Guard;
import com.google.code.tempusfugit.concurrency.Callable;
import com.google.code.tempusfugit.concurrency.annotations.ThreadSafe;

import static bad.robot.pingpong.shared.memory.Increment.increment;
import static bad.robot.pingpong.shared.memory.optimistic.CallableAdaptors.onAbort;
import static bad.robot.pingpong.shared.memory.optimistic.CallableAdaptors.onCommit;
import static bad.robot.pingpong.shared.memory.optimistic.RunAtomically.runAtomically;

@ThreadSafe
public class ContentionMonitoringStmGuard implements Guard, ContentionMonitoringStmGuardMBean {

    private final Counter aborts = new AtomicLongCounter();
    private final Counter commits = new AtomicLongCounter();

    @Override
    public <R, E extends Exception> R execute(Callable<R, E> callable) throws E {
        return runAtomically(callable, onCommit(increment(commits)), onAbort(increment(aborts)));
    }

    @Override
    public Boolean guarding() {
        return true;
    }

    @Override
    public Double getContentionRatio() {
        return (double) aborts.get() / (double) commits.get();
    }
}

