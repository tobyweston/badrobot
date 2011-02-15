package bad.robot.pingpong.shared.memory.pessimistic;

import com.google.code.tempusfugit.concurrency.Callable;

import java.util.concurrent.locks.Lock;

import static bad.robot.pingpong.shared.memory.pessimistic.AcquireLock.acquired;

public class LockingGuard implements Guard {

    private final Lock lock;

    public LockingGuard(Lock lock) {
        this.lock = lock;
    }

    @Override
    public <R, E extends Exception> R execute(Callable<R, E> callable) throws E {
        try {
            lock.lock();
            return callable.call();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Boolean guarding() {
        return acquired(lock);
    }
}
