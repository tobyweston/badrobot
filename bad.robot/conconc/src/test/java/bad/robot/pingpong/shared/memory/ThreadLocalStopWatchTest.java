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

import bad.robot.pingpong.ClassInvariantViolation;
import bad.robot.pingpong.StopWatch;
import org.junit.Test;

import java.util.Date;

import static com.google.code.tempusfugit.temporal.Duration.millis;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ThreadLocalStopWatchTest {

    private final MovableClock clock = new MovableClock(new Date(10000));
    private final StopWatch timer = new ThreadLocalStopWatch(clock);

    @Test
    public void startedButNotStopped() {
        timer.start();
        assertThat(timer.elapsedTime(), is(millis(0)));
    }

    @Test
    public void shouldRecordElapsedTimeBetweenStartAndStop() {
        timer.start();
        clock.incrementBy(millis(5));
        timer.stop();
        assertThat(timer.elapsedTime(), is(millis(5)));
        clock.incrementBy(millis(5));
        timer.stop();
        assertThat(timer.elapsedTime(), is(millis(10)));
        timer.start();
        clock.incrementBy(millis(20));
        timer.stop();
        assertThat(timer.elapsedTime(), is(millis(20)));
    }

    @Test
    public void stoppedButNotStarted() {
        timer.stop();
        assertThat(timer.elapsedTime(), is(millis(0)));
    }

    @Test (expected = ClassInvariantViolation.class)
    public void stoppedThenStarted() {
        timer.stop();
        clock.incrementBy(millis(100));
        timer.start();
        assertThat(timer.elapsedTime(), is(millis(-100)));
    }
}
