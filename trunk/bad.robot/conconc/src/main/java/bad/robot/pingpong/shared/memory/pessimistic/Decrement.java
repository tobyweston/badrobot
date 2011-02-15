package bad.robot.pingpong.shared.memory.pessimistic;

import com.google.code.tempusfugit.concurrency.Callable;

import java.util.concurrent.atomic.AtomicLong;

public class Decrement implements Callable<Void, RuntimeException> {

    private final AtomicLong counter;

    public static Decrement decrement(AtomicLong counter) {
        return new Decrement(counter);
    }

    private Decrement(AtomicLong counter) {
        this.counter = counter;
    }

    @Override
    public Void call() throws RuntimeException {
        counter.getAndDecrement();
        return null;
    }
}
