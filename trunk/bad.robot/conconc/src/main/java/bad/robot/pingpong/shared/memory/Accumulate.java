package bad.robot.pingpong.shared.memory;

import com.google.code.tempusfugit.concurrency.Callable;

public class Accumulate<T> implements Callable<Void, RuntimeException> {

    private final T value;
    private final AccumulatingCounter<T> counter;

    public static <T> Accumulate<T> add(T value, To<T> counter) {
        return new Accumulate<T>(value, counter.value);
    }

    public Accumulate(T value, AccumulatingCounter<T> counter) {
        this.value = value;
        this.counter = counter;
    }

    @Override
    public Void call() throws RuntimeException {
        counter.add(value);
        return null;
    }

    public static class To<T> {

        private final AccumulatingCounter<T> value;

        public static <T> To<T> to(AccumulatingCounter<T> counter) {
            return new To<T>(counter);
        }

        private To(AccumulatingCounter<T> value) {
            this.value = value;
        }
    }

}
