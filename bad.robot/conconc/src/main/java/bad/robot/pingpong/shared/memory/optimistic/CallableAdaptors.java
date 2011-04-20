package bad.robot.pingpong.shared.memory.optimistic;

import com.google.code.tempusfugit.concurrency.Callable;

public final class CallableAdaptors {

    public static CompensatingTask onAbort(final Callable<?, RuntimeException> callable) {
        return new CompensatingTask() {
            @Override
            public void run() {
                callable.call();
            }
        };
    }

    public static DeferredTask onCommit(final Callable<?, RuntimeException> callable) {
        return new DeferredTask() {
            @Override
            public void run() {
                callable.call();
            }
        };
    }
}
