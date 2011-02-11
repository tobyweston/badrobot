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

package bad.robot.pingpong.instrumentation.threads;

import bad.robot.pingpong.shared.memory.ThreadCounter;

import java.util.concurrent.ThreadFactory;

public class InstrumentingThreadFactory implements ThreadFactory {

    private final ThreadCounter counter;

    public InstrumentingThreadFactory(ThreadCounter counter) {
        this.counter = counter;
    }

    public Thread newThread(final Runnable runnable) {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    counter.incrementActiveThreads();
                    runnable.run();
                } finally {
                    counter.decrementActiveThreads();
                }
            }
        });
        counter.incrementCreatedThreads();
        return thread;
    }
}
