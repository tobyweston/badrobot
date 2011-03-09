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

package bad.robot.pingpong.shared.memory.pessimistic;

import bad.robot.pingpong.shared.memory.AbstractThreadCounterIntegrationTest;
import bad.robot.pingpong.shared.memory.ThreadCounter;
import org.junit.BeforeClass;

import static bad.robot.pingpong.shared.memory.pessimistic.Unguarded.unguarded;

public class ThreadCounterIntegrationTest extends AbstractThreadCounterIntegrationTest {

    @BeforeClass
    public static void createThreadCounter() {
        counter = new ThreadCounter(unguarded(), new AtomicLongCounter(), new AtomicLongCounter());
    }

}
