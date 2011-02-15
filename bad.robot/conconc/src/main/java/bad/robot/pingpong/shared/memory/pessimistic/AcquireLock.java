package bad.robot.pingpong.shared.memory.pessimistic;

import com.google.code.tempusfugit.concurrency.Interruptible;

import java.util.concurrent.locks.Lock;

import static com.google.code.tempusfugit.concurrency.ThreadUtils.resetInterruptFlagWhen;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class AcquireLock {

    public static Boolean acquired(final Lock lock) {
        return resetInterruptFlagWhen(new Interruptible<Boolean>() {
            @Override
            public Boolean call() throws InterruptedException {
                return lock.tryLock(10, MILLISECONDS);
            }
        });
    }
}
