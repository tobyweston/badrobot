package bad.robot.pingpong.shared.memory;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class LongCounterTest {

    private final LongCounter counter = new LongCounter();

    @Test
    public void shouldInitialise() {
        assertThat(counter.get(), is(0L));
    }

    @Test
    public void shouldIncrement() {
        counter.increment();
        assertThat(counter.get(), is(1L));
    }

    @Test
    public void shouldDecrement() {
        counter.decrement();
        assertThat(counter.get(), is(-1L));
    }

    @Test
    public void shouldReset() {
        counter.increment();
        counter.reset();
        assertThat(counter.get(), is(0L));
    }

}
