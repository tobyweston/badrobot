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

import com.google.code.tempusfugit.temporal.Duration;
import org.hamcrest.Description;
import org.junit.internal.matchers.TypeSafeMatcher;

public class ThreadPoolTimerMeanExecutionTimeMatcher extends TypeSafeMatcher<ThreadPoolTimer> {

    private final Duration expectedMeanExecutionTime;

    public static ThreadPoolTimerMeanExecutionTimeMatcher hasMeanElapsedTimeOf(Duration expectedMeanElapsedTime) {
        return new ThreadPoolTimerMeanExecutionTimeMatcher(expectedMeanElapsedTime);
    }

    ThreadPoolTimerMeanExecutionTimeMatcher(Duration expectedMeanExecutionTime) {
        this.expectedMeanExecutionTime = expectedMeanExecutionTime;
    }

    @Override
    public boolean matchesSafely(ThreadPoolTimer timer) {
        return timer.getMeanExecutionTime().equals(expectedMeanExecutionTime.inMillis());
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(expectedMeanExecutionTime);
    }
}
