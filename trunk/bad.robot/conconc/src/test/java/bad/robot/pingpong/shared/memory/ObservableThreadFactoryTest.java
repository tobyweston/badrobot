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

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class ObservableThreadFactoryTest {

    private final Mockery context = new Mockery();
    private final ThreadObserver observer = context.mock(ThreadObserver.class);
    private final ObservableThreadFactory factory = new ObservableThreadFactory(observer);

    @Test
    public void willDelegateToRunnable() {
        final Runnable runnable = context.mock(Runnable.class);
        context.checking(new Expectations(){{
            one(runnable).run();
            ignoring(observer);
        }});
        factory.newThread(runnable).run();
    }
    
    @Test
    public void willNotifyThreadStarted() {
        context.checking(new Expectations(){{
            one(observer).threadStarted();
            ignoring(observer).threadTerminated();
            ignoring(observer).threadCreated();
        }});
        factory.newThread(stub()).run();
    }

    @Test
    public void willNotifyThreadTermination() {
        context.checking(new Expectations(){{
            ignoring(observer).threadStarted();
            one(observer).threadTerminated();
            ignoring(observer).threadCreated();
        }});
        factory.newThread(stub()).run();
    }

    @Test (expected = RuntimeException.class)
    public void willNotifyThreadTerminationOnException() {
        final Runnable runnable = context.mock(Runnable.class);
        context.checking(new Expectations() {{
            one(runnable).run(); will(throwException(new RuntimeException()));
            ignoring(observer).threadStarted();
            one(observer).threadTerminated();
            ignoring(observer).threadCreated();
        }});
        factory.newThread(runnable).run();
    }

    @Test
    public void willNotifyThreadCreated() {
        context.checking(new Expectations(){{
            never(observer).threadStarted();
            never(observer).threadTerminated();
            one(observer).threadCreated();
        }});
        factory.newThread(stub());
    }

    private static Runnable stub() {
        return new Runnable() {
            @Override
            public void run() {
            }
        };
    }
}
