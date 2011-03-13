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
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.*;

import static com.google.code.tempusfugit.concurrency.ExecutorServiceShutdown.shutdown;
import static com.google.code.tempusfugit.temporal.Duration.millis;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@RunWith(JMock.class)
public class ObservableThreadPoolExecutorIntegrationTest {

    private final Mockery context = new Mockery();

    private final ThreadPoolObserver observer = context.mock(ThreadPoolObserver.class);
    private final ThreadPoolExecutor threads = new ObservableThreadPoolExecutor(1, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread thread, Throwable e) {
                    System.out.printf("%s %s%n", e.getClass(), e.getMessage());
                    for (StackTraceElement element : e.getStackTrace())
                        System.out.println("   " + element);
                    System.out.printf("thrown from %s %n", thread.getName());
                }
            });
            return thread;
        }
    }, observer);

    @Test
    public void submitWillNotifyObserver() throws TimeoutException, ExecutionException, InterruptedException {
        context.checking(new Expectations() {{
            oneOf(observer).beforeExecute(with(any(Thread.class)), with(any(Runnable.class)));
            oneOf(observer).afterExecute(with(any(Runnable.class)), with(any(Throwable.class)));
        }});
        Future<?> future = threads.submit(doNothing());
        future.get(500, MILLISECONDS);
    }

    @Test (expected = Exception.class)
    public void taskThrowsException() throws TimeoutException, ExecutionException, InterruptedException {
        context.checking(new Expectations() {{
            oneOf(observer).beforeExecute(with(any(Thread.class)), with(any(Runnable.class)));
            oneOf(observer).afterExecute(with(any(Runnable.class)), with(any(Throwable.class)));
        }});
        Future<?> future = threads.submit(throwsException());
        future.get(500, MILLISECONDS);
    }

    @After
    public void shutdownThreadPool() throws Throwable {
        context.checking(new Expectations(){{
            oneOf(observer).terminated();
        }});
        shutdown(threads).waitingForCompletion(millis(500));
    }

    private static Runnable doNothing() {
        return new Runnable() {
            @Override
            public void run() {
            }
        };
    }

    private static Callable throwsException() {
        return new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                throw new Exception("boo");
            }
        };
    }
}
