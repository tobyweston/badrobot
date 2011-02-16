package bad.robot.pingpong.shared.memory;

import com.google.code.tempusfugit.concurrency.Callable;

public class Decrement<T extends Counter> implements Callable<Void, RuntimeException> {

    private final T counter;

    public static <T extends Counter> Decrement<T> decrement(T counter) {
        return new Decrement<T>(counter);
    }

    private Decrement(T counter) {
        this.counter = counter;
    }

    @Override
    public Void call() throws RuntimeException {
        counter.decrement();
        return null;
    }
}
