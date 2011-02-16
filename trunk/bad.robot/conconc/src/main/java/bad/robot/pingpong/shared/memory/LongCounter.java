package bad.robot.pingpong.shared.memory;

public class LongCounter implements Counter {

    private Long count = new Long(0);

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
        count = new Long(0);
    }

    public Long get() {
        return count;
    }
}
