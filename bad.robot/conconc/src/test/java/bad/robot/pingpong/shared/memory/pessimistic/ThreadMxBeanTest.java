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

package bad.robot.pingpong.shared.memory.pessimistic;

import com.google.code.tempusfugit.concurrency.IntermittentTestRunner;
import com.google.code.tempusfugit.concurrency.annotations.Intermittent;
import com.google.code.tempusfugit.temporal.Condition;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.google.code.tempusfugit.concurrency.ThreadUtils.sleep;
import static com.google.code.tempusfugit.temporal.Duration.days;
import static com.google.code.tempusfugit.temporal.Duration.millis;
import static com.google.code.tempusfugit.temporal.Timeout.timeout;
import static com.google.code.tempusfugit.temporal.WaitFor.waitOrTimeout;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test shows that when using the {@link ThreadMXBean#getThreadInfo(long)}, you can't keep a reference to the
 * {@link java.lang.management.ThreadInfo}; you have to ask the question on demand. Change the {@link #blockCountFor(Thread, org.hamcrest.Matcher}
 * method below to use a cached reference instead of using it on demand and the test will fail (for the monitor usage).
 * <p/>
 * It also shows that the block count can not be used with {@link Lock}s (as locks use {@link java.util.concurrent.locks.LockSupport#park()}
 * and block count will only show synchronised (BLOCKED) and waiting (TIMED_WAITING, WAITING)). In this case, we should
 * use the wait count.
 *
 * @see {@link ContentionRecordingLongCounter}
 */
@RunWith(IntermittentTestRunner.class)
public class ThreadMxBeanTest {

    private final ThreadMXBean jvm = ManagementFactory.getThreadMXBean();

    @Before
    public void setupThreadMonitoring() {
        assertThat(jvm.isThreadContentionMonitoringSupported(), is(true));
        jvm.setThreadContentionMonitoringEnabled(true);
    }

    @Test
    @Intermittent(repetition = 30)
    public void threadInfoShouldReflectMonitorBlockCount() throws TimeoutException, InterruptedException {
        Thread thread1 = waitForStartup(new AcquireMonitor(this));
        Thread thread2 = startAsThread(new AcquireMonitor(this));
        waitOrTimeout(blockCountFor(thread2, is(1L)), timeout(millis(250)));
        waitOrTimeout(waitCountFor(thread2, is(0L)), timeout(millis(250)));
        thread1.interrupt();
        thread2.interrupt();
    }

    @Test
    @Intermittent(repetition = 30)
    public void threadInfoShouldReflectLockBlockCount() throws TimeoutException, InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Thread thread1 = waitForStartup(new AcquireLock(lock));
        Thread thread2 = startAsThread(new AcquireLock(lock));
        waitOrTimeout(waitCountFor(thread2, is(greaterThanOrEqualTo(1L))), timeout(millis(250)));
        waitOrTimeout(blockCountFor(thread2, is(0L)), timeout(millis(250)));
        thread1.interrupt();
        thread2.interrupt();
    }

    private static Thread waitForStartup(AcquireMonitor monitor) throws InterruptedException {
        Thread thread = startAsThread(monitor);
        monitor.waitForMonitorAcquisition();
        return thread;
    }

    private static Thread waitForStartup(AcquireLock monitor) throws InterruptedException {
        Thread thread = startAsThread(monitor);
        monitor.waitForLockAcquisition();
        return thread;
    }

    private Condition blockCountFor(final Thread thread, final Matcher<Long> matcher) {
        return new Condition() {
            @Override
            public boolean isSatisfied() {
                return matcher.matches(jvm.getThreadInfo(thread.getId()).getBlockedCount());
            }
        };
    }

    private Condition waitCountFor(final Thread thread, final Matcher<Long> matcher) {
        return new Condition() {
            @Override
            public boolean isSatisfied() {
                return matcher.matches(jvm.getThreadInfo(thread.getId()).getWaitedCount());
            }
        };
    }

    private static Thread startAsThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
        return thread;
    }

    private class AcquireMonitor implements Runnable {
        private final CountDownLatch monitorAcquired;
        private final Object monitor;

        public AcquireMonitor(Object monitor) {
            this.monitorAcquired = new CountDownLatch(1);
            this.monitor = monitor;
        }

        public void waitForMonitorAcquisition() throws InterruptedException {
            monitorAcquired.await();
        }

        @Override
        public void run() {
            synchronized (monitor) {
                monitorAcquired.countDown();
                sleep(days(365));
            }
        }
    }

    private class AcquireLock implements Runnable {
        private final CountDownLatch lockAcquired;
        private final Lock lock;

        public AcquireLock(Lock lock) {
            this.lockAcquired = new CountDownLatch(1);
            this.lock = lock;
        }

        public void waitForLockAcquisition() throws InterruptedException {
            lockAcquired.await();
        }

        @Override
        public void run() {
            try {
//                if (lock.tryLock() == false)
//                    ThreadDump.dumpThreads(System.out);
                lock.lock();
                lockAcquired.countDown();
                sleep(days(365));
            } finally {
                lock.unlock();
            }
        }
    }

}
