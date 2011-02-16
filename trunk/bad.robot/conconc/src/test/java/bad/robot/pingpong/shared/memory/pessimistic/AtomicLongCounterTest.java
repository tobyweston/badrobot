package bad.robot.pingpong.shared.memory.pessimistic;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AtomicLongCounterTest {

    private final AtomicLongCounter counter = new AtomicLongCounter();

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
