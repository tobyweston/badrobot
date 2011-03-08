package bad.robot.pingpong.shared.memory.optimistic;

import akka.stm.Ref;
import bad.robot.pingpong.shared.memory.Counter;
import com.google.code.tempusfugit.concurrency.annotations.Not;
import com.google.code.tempusfugit.concurrency.annotations.ThreadSafe;

@Not(ThreadSafe.class)
public class TransactionalReferenceCounter implements Counter {

    private final Ref<Long> count = new Ref<Long>(0L);

    @Override
    public void increment() {
        count.set(count.get() + 1L);
    }

    @Override
    public void decrement() {
        count.set(count.get() - 1);
    }

    @Override
    public Long get() {
        return count.get();
    }

    @Override
    public void reset() {
        count.set(0L);
    }
}
