package bad.robot.pingpong.shared.memory.pessimistic;

import bad.robot.pingpong.shared.memory.Counter;
import bad.robot.pingpong.shared.memory.CounterFactory;

public class AtomicLongCounterFactory implements CounterFactory {

    @Override
    public Counter create() {
        return new AtomicLongCounter();
    }
}
