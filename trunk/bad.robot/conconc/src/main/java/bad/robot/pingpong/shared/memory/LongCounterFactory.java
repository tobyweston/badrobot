package bad.robot.pingpong.shared.memory;

public class LongCounterFactory implements CounterFactory {

    @Override
    public Counter create() {
        return new LongCounter();
    }
}
