package bad.robot.pingpong.shared.memory.pessimistic;

import bad.robot.pingpong.shared.memory.AbstractCounterTest;
import bad.robot.pingpong.shared.memory.Counter;

public class AtomitcLongCounterTest extends AbstractCounterTest {

    @Override
    protected Counter createCounterUnderTest() {
        return new AtomicLongCounter();
    }
}
