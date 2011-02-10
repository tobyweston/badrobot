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

package bad.robot.pingpong.memory.shared.pessimistic;

import bad.robot.pingpong.memory.shared.ThreadStatistics;

public class GuardedThreadStatistics implements ThreadStatistics {

    private long activeThreads;
    private long threadCount;

    @Override
    public void incrementActiveThreads() {
        activeThreads++;
    }

    @Override
    public void decrementActiveThreads() {
        activeThreads--;
    }

    @Override
    public void incrementThreadCount() {
        threadCount++;
    }

    @Override
    public long getActiveThreads() {
        return activeThreads;
    }

    @Override
    public long getThreadCount() {
        return threadCount;
    }

    @Override
    public void reset() {
        threadCount = 0;
        activeThreads = 0;
    }

}
