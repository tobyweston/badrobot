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
import com.google.code.tempusfugit.temporal.Duration;
import org.junit.Test;

import static bad.robot.pingpong.shared.memory.ThreadPoolTimerMeanExecutionTimeMatcher.hasMeanElapsedTimeOf;
import static com.google.code.tempusfugit.temporal.Duration.millis;
import static org.junit.Assert.assertThat;

public class ThreadPoolTimerTest {

    private static final Throwable NO_EXCEPTION = null;

    private final StopWatchStub stopWatch = new StopWatchStub();
    private final ThreadPoolTimer timer = new ThreadPoolTimer(stopWatch);

    @Test
    public void shouldGetMeanExecutionTime() {
        timer.beforeExecute(newThread(), newRunnable());
        timer.afterExecute(newRunnable(), NO_EXCEPTION);
        assertThat(timer, hasMeanElapsedTimeOf(millis(0)));

        stopWatch.setElapsedTime(millis(500));
        timer.beforeExecute(newThread(), newRunnable());
        timer.afterExecute(newRunnable(), NO_EXCEPTION);
        assertThat(timer, hasMeanElapsedTimeOf(millis(250)));

        stopWatch.setElapsedTime(millis(1500));
        timer.beforeExecute(newThread(), newRunnable());
        timer.afterExecute(newRunnable(), NO_EXCEPTION);
        assertThat(timer, hasMeanElapsedTimeOf(millis(666)));

        stopWatch.setElapsedTime(millis(600));
        timer.beforeExecute(newThread(), newRunnable());
        timer.afterExecute(newRunnable(), NO_EXCEPTION);
        assertThat(timer, hasMeanElapsedTimeOf(millis(650)));
    }

    private class StopWatchStub implements StopWatch {

        private Duration elapsedTime = millis(0);

        @Override
        public void start() {
        }

        @Override
        public void stop() {
        }

        @Override
        public Duration elapsedTime() {
            return elapsedTime;
        }

        public void setElapsedTime(Duration elapsedTime) {
            this.elapsedTime = elapsedTime;
        }
    }


    private static Thread newThread() {
        return new Thread();
    }

    private static Runnable newRunnable() {
        return new Runnable(){
            @Override
            public void run() {
            }
        };
    }

}
