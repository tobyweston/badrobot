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

import bad.robot.pingpong.StopWatch;
import bad.robot.pingpong.shared.memory.pessimistic.Guard;
import com.google.code.tempusfugit.temporal.Duration;

import static bad.robot.pingpong.shared.memory.Accumulate.To.to;
import static bad.robot.pingpong.shared.memory.Accumulate.add;
import static bad.robot.pingpong.shared.memory.Divide.Divisor.by;
import static bad.robot.pingpong.shared.memory.Divide.divide;
import static bad.robot.pingpong.shared.memory.Increment.increment;

public class ThreadPoolTimer implements ThreadPoolObserver, ThreadPoolTimerMBean {

    private final Guard guard;
    private final StopWatch timer;
    private final Counter tasks;
    private final Counter terminated;
    private final AccumulatingCounter<Duration> totalTime;

    public ThreadPoolTimer(Guard guard, StopWatch timer, Counter tasks, Counter terminated, AccumulatingCounter<Duration> totalTime) {
        this.timer = timer;
        this.tasks = tasks;
        this.terminated = terminated;   
        this.totalTime = totalTime;
        this.guard = guard;
    }

    @Override
    public void beforeExecute(Thread thread, Runnable task) {
        assert (Thread.currentThread().equals(thread));
        timer.start();
        guard.execute(increment(tasks));
    }

    @Override
    public void afterExecute(Runnable task, Throwable throwable) {
        timer.stop();
        guard.execute(add(timer.elapsedTime(), to(totalTime)));
    }

    @Override
    public void terminated() {
        guard.execute(increment(terminated));
    }

    @Override
    public Long getNumberOfExecutions() {
        return tasks.get();
    }

    @Override
    public Long getTotalTime() {
        return totalTime.get();
    }

    @Override
    public Long getMeanExecutionTime() {
        return guard.execute(divide(totalTime, by(tasks)));
    }

    @Override
    public Long getTerminated() {
        return terminated.get();
    }

    @Override
    public void reset() {
        totalTime.reset();
        tasks.reset();
        terminated.reset();
    }
}
