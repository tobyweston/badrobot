package bad.robot.pingpong.shared.memory;

import com.google.code.tempusfugit.concurrency.Callable;

import static com.google.code.tempusfugit.ExceptionWrapper.wrapAsRuntimeException;

public class AllOf implements Callable<Void, RuntimeException> {

    private final Callable<?, ?>[] callables;

    public static AllOf allOf(Callable<?, ?>... callables) {
        return new AllOf(callables);
    }

    private AllOf(Callable<?, ?>... callables) {
        this.callables = callables;
    }

    @Override
    public Void call() throws RuntimeException {
        for (Callable<?, ?> callable : callables)
            wrapAsRuntimeException(callable);
        return null;
    }
}
