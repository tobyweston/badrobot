/*
 * Copyright (c) 2009-2010, bad robot (london) ltd
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

package bad.robot.pingpong.instrument;

import bad.robot.pingpong.memory.shared.ThreadStatistics;

import java.util.concurrent.ThreadFactory;

public class InstrumentingThreadFactory implements ThreadFactory {

    private final ThreadStatistics statistics;

    public InstrumentingThreadFactory(ThreadStatistics statistics) {
        this.statistics = statistics;
    }

    public Thread newThread(final Runnable runnable) {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    statistics.incrementActiveThreads();
                    runnable.run();
                } finally {
                    statistics.decrementActiveThreads();
                }
            }
        });
        statistics.incrementThreadCount();
        return thread;
    }
}
