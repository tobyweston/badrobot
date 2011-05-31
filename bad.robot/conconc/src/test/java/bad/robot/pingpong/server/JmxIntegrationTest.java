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

package bad.robot.pingpong.server;

import bad.robot.pingpong.shared.memory.RealClock;
import bad.robot.pingpong.shared.memory.ThreadLocalStopWatch;
import bad.robot.pingpong.shared.memory.ThreadPoolTimer;
import bad.robot.pingpong.shared.memory.optimistic.OptimisticThroughput;
import bad.robot.pingpong.shared.memory.optimistic.stm.ContentionMonitoringStmGuard;
import bad.robot.pingpong.shared.memory.optimistic.stm.StmAtomicLongCounter;
import bad.robot.pingpong.shared.memory.optimistic.stm.StmMillisecondCounter;
import bad.robot.pingpong.shared.memory.pessimistic.PessimisticThreadCounters;
import bad.robot.pingpong.shared.memory.pessimistic.PessimisticThreadPoolTimers;
import bad.robot.pingpong.shared.memory.pessimistic.synchronised.ContentionMonitoringGuard;
import org.junit.Test;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;

import static bad.robot.pingpong.shared.memory.optimistic.OptimisticThroughput.createThreadSafeThroughput;
import static bad.robot.pingpong.shared.memory.pessimistic.PessimisticThreadCounters.createThreadSafeCounterMaintainingInvariant;

public class JmxIntegrationTest {

    @Test
    public void canRegisterThreadObserver() throws InstanceNotFoundException, MBeanRegistrationException {
        Jmx.register(PessimisticThreadCounters.createThreadSafeCounterMaintainingInvariant(), "1");
        Jmx.unregister("1");
    }

    @Test
    public void canRegisterThreadPoolObserver() {
        Jmx.register(PessimisticThreadPoolTimers.createThreadSafeThreadPoolTimer(), "2");
        Jmx.unregister("2");
    }

    @Test
    public void canRegisterThroughput() {
        Jmx.register(OptimisticThroughput.createThreadSafeThroughput(), "3");
        Jmx.unregister("3");
    }

    @Test
    public void canRegisterContentionMonitor() {
        Jmx.register(new ContentionMonitoringGuard(createThreadSafeThroughput()), "4");
        Jmx.unregister("4");
    }

    @Test
    public void canRegisterStmContentionMonitor() {
        Jmx.register(new ContentionMonitoringStmGuard(), "5");
        Jmx.unregister("5");
    }

    @Test
    public void exampleUsageOfRegisteringRelatedComponents() {
        Jmx.register(new ContentionMonitoringStmGuard(), "Contention-ThreadPool-1");
        Jmx.register(new ThreadPoolTimer(new ContentionMonitoringStmGuard(), new ThreadLocalStopWatch(new RealClock()), new StmAtomicLongCounter(), new StmAtomicLongCounter(), new StmMillisecondCounter()), "Throughput-ThreadPool-1");
        Jmx.unregister("Contention-ThreadPool-1");
        Jmx.unregister("Throughput-ThreadPool-1");
    }

    @Test
    public void canRegisterTheSameComponentTwice() {
        Jmx.register(createThreadSafeCounterMaintainingInvariant(), "1");
        Jmx.register(createThreadSafeCounterMaintainingInvariant(), "1");
        Jmx.unregister("1");
    }

}
