package bad.robot.pingpong.shared.memory.pessimistic;

import bad.robot.pingpong.shared.memory.Counter;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongCounter implements Counter {

    private final AtomicLong count = new AtomicLong();

    @Override
    public void increment() {
        count.getAndIncrement();
    }

    @Override
    public void decrement() {
        count.getAndDecrement();
    }

    @Override
    public void reset() {
        count.set(0);
    }

    public Long get() {
        return count.get();
    }
}
