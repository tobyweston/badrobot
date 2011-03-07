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
    public void reset() {
        count = 0;
    }

    public Long get() {
        return count;
    }
}
