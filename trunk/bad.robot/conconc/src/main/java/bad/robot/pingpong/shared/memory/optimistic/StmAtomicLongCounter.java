package bad.robot.pingpong.shared.memory.optimistic;

import akka.stm.Atomic;
import akka.stm.Ref;
import bad.robot.pingpong.shared.memory.Counter;
import com.google.code.tempusfugit.concurrency.annotations.ThreadSafe;

@ThreadSafe
public class StmAtomicLongCounter implements Counter {

    private final Ref<Long> count = new Ref<Long>(0L);

    @Override
    public void increment() {
        new Atomic<Long>() {
            @Override
            public Long atomically() {
                return count.set(count.get() + 1L);
            }
        }.execute();
    }

    @Override
    public void decrement() {
        new Atomic<Long>() {
            @Override
            public Long atomically() {
                return count.set(count.get() - 1L);
            }
        }.execute();
    }

    @Override
    public Long get() {
        return count.get();
    }

    @Override
    public void reset() {
        new Atomic<Long>() {
            @Override
            public Long atomically() {
                return count.set(0L);
            }
        }.execute();
    }
}
