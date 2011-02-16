package bad.robot.pingpong.shared.memory;

import com.google.code.tempusfugit.concurrency.Callable;

public class Increment<T extends Counter> implements Callable<Void, RuntimeException> {

    private final Counter counter;

    public static <T extends Counter> Increment<T> increment(T counter) {
        return new Increment<T>(counter);
    }

    private Increment(T counter) {
        this.counter = counter;
    }

    @Override
    public Void call() throws RuntimeException {
        counter.increment();
        return null;
    }
}
