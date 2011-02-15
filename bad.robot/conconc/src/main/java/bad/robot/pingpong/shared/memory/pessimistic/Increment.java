package bad.robot.pingpong.shared.memory.pessimistic;

import com.google.code.tempusfugit.concurrency.Callable;

import java.util.concurrent.atomic.AtomicLong;

public class Increment implements Callable<Void, RuntimeException> {

    private final AtomicLong counter;

    public static Increment increment(AtomicLong counter) {
        return new Increment(counter);
    }

    private Increment(AtomicLong counter) {
        this.counter = counter;
    }

    @Override
    public Void call() throws RuntimeException {
        counter.getAndIncrement();
        return null;
    }
}
