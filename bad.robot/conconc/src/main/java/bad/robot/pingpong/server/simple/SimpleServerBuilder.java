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

package bad.robot.pingpong.server.simple;

import bad.robot.pingpong.server.Jmx;
import bad.robot.pingpong.shared.memory.ObservableThreadFactory;
import bad.robot.pingpong.shared.memory.ThreadObserver;
import bad.robot.pingpong.shared.memory.ThreadPoolObserver;
import bad.robot.pingpong.shared.memory.ThreadPoolTimer;

import java.io.IOException;

import static bad.robot.pingpong.shared.memory.Executors.newFixedThreadPool;
import static bad.robot.pingpong.shared.memory.pessimistic.PessimisticThreadCounters.createThreadSafeCounterMaintainingInvariant;
import static bad.robot.pingpong.shared.memory.pessimistic.PessimisticThreadPoolTimers.createThreadSafeThreadPoolTimer;

public class SimpleServerBuilder {

    private static final int REQUEST_PROCESSING_THREADS = 20;

    public SimpleServer build() throws IOException {
        ThreadObserver threadCounter = createThreadSafeCounterMaintainingInvariant();
        ThreadPoolTimer timer = createThreadSafeThreadPoolTimer();
        registerWithJmx(threadCounter, timer);
        return new SimpleServer(newFixedThreadPool(REQUEST_PROCESSING_THREADS, new ObservableThreadFactory(threadCounter), timer));
    }

    private static void registerWithJmx(ThreadObserver threadCounter, ThreadPoolObserver timer) {
        Jmx.register(threadCounter, "ThreadCounter");
        Jmx.register(timer, "ThreadPool");
    }
}
