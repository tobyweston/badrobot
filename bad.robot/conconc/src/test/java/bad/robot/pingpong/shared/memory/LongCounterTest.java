package bad.robot.pingpong.shared.memory;

public class LongCounterTest extends AbstractCounterTest {

    @Override
    protected Counter createCounterUnderTest() {
        return new LongCounter();
    }
}
