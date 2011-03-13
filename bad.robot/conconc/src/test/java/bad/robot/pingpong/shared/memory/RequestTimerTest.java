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
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.code.tempusfugit.temporal.Duration.millis;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(JMock.class)
public class RequestTimerTest {

    private final Mockery context = new Mockery();
    private final StopWatch stopWatch = context.mock(StopWatch.class);
    private final RequestTimer timer = new RequestTimer(stopWatch);

    @Test
    public void shouldTimeRequest() {
        context.checking(new Expectations(){{
            one(stopWatch).start(); 
            one(stopWatch).stop();
            one(stopWatch).elapsedTime(); will(returnValue(millis(606)));
        }});
        RequestObserver.Request request = timer.started();
        assertThat(request.finished(), is(millis(606)));
    }
}
