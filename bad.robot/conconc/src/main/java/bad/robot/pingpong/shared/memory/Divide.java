package bad.robot.pingpong.shared.memory;

import com.google.code.tempusfugit.concurrency.Callable;


public class Divide implements Callable<Long, RuntimeException> {

    private AccumulatingCounter<?> dividend;
    private Counter divisor;

    public static Divide divide(AccumulatingCounter<?> dividend, Divisor divisor) {
        return new Divide(dividend, divisor);
    }

    private Divide(AccumulatingCounter<?> dividend, Divisor divisor) {
        this.dividend = dividend;
        this.divisor = divisor.value;
    }

    @Override
    public Long call() throws RuntimeException {
        return dividend.get() / divisor.get();
    }

    public static class Divisor {
        private static Counter value;

        public static Divisor by(Counter counter) {
            return new Divisor(counter);
        }

        private Divisor(Counter value) {
            this.value = value;
        }
    }
}
