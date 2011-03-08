package bad.robot.pingpong.shared.memory;

public class LongCounter implements Counter {

    private volatile long count = 0;

    @Override
    public void increment() {
        count++;
    }

    @Override
    public void decrement() {
        count--;
    }

    @Override
    public Long get() {
        return count;
    }

    @Override
    public void reset() {
        count = 0;
    }
}
