package bad.robot.pingpong.shared.memory;

import com.google.code.tempusfugit.concurrency.Callable;

import java.util.List;

import static java.util.Arrays.asList;

public class Reset<T extends Counter> implements Callable<Void, RuntimeException> {

    private final List<T> counters;

    public static <T extends Counter> Reset<T> resetOf(T... counters) {
        return new Reset<T>(counters);
    }

    private Reset(T[] counters) {
        this.counters = asList(counters);
    }

    @Override
    public Void call() throws RuntimeException {
        for (T counter : counters)
            counter.reset();
        return null;
    }
}
