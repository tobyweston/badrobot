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

package bad.robot.pingpong.shared.memory.optimistic;

import com.google.code.tempusfugit.concurrency.Callable;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import static bad.robot.pingpong.shared.memory.optimistic.RunAtomically.runAtomically;

@RunWith(JMock.class)
public class RunAtomicallyTest {

    private final Mockery context = new Mockery();
    private final Callable callable = context.mock(Callable.class);

    @Test
    public void shouldDelegate() throws Exception {
        context.checking(new Expectations(){{
            one(callable).call();
        }});
        runAtomically(callable);
    }

    @Test
    public void shouldDelegateOnAtomically() throws Exception {
        context.checking(new Expectations(){{
            one(callable).call();
        }});
        new RunAtomically(callable, new DoNothingDeferredTask(), new DoNothingCompensatingTask()).execute();
    }

    @Test(expected = RuntimeException.class)
    public void shouldWrapException() throws Exception {
        context.checking(new Expectations(){{
            one(callable).call(); will(throwException(new Exception()));
        }});
        runAtomically(callable);
    }

    @Test(expected = Error.class)
    public void shouldRethrowError() throws Exception {
        context.checking(new Expectations(){{
            one(callable).call(); will(throwException(new Error()));
        }});
        runAtomically(callable);
    }
}
