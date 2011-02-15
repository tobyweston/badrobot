package bad.robot.pingpong.shared.memory.pessimistic;

import com.google.code.tempusfugit.concurrency.Callable;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Arrays.asList;

public class Reset implements Callable<Void, RuntimeException> {

    private final List<AtomicLong> counters;

    public static Reset resetOf(AtomicLong... counters) {
        return new Reset(counters);
    }

    private Reset(AtomicLong... counters) {
        this.counters = asList(counters);
    }

    @Override
    public Void call() throws RuntimeException {
        for (AtomicLong counter : counters)
            counter.set(0);
        return null;
    }
}
