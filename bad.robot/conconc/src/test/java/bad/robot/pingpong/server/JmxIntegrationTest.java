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

import org.junit.After;
import org.junit.Test;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;

import static bad.robot.pingpong.shared.memory.pessimistic.PessimisticThreadCounters.createThreadSafeCounterMaintainingInvariant;
import static bad.robot.pingpong.shared.memory.pessimistic.PessimisticThreadPoolTimers.createThreadSafeThreadPoolTimer;

public class JmxIntegrationTest {

    @Test
    public void canRegisterThreadObserver() throws InstanceNotFoundException, MBeanRegistrationException {
        Jmx.register(createThreadSafeCounterMaintainingInvariant(), "1");
        Jmx.unregister(createThreadSafeCounterMaintainingInvariant(), "1");
    }

    @Test
    public void canRegisterThreadPoolObserver() {
        Jmx.register(createThreadSafeThreadPoolTimer(), "2");
        Jmx.unregister(createThreadSafeThreadPoolTimer(), "2");
    }

    @Test
    public void canRegisterTheSameComponentTwice() {
        Jmx.register(createThreadSafeCounterMaintainingInvariant(), "1");
        Jmx.register(createThreadSafeCounterMaintainingInvariant(), "1");
    }

    @After
    public void unregister() {

    }

}
