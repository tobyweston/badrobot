package bad.robot.pingpong.shared.memory.optimistic;

import akka.stm.Atomic;
import com.google.code.tempusfugit.concurrency.Callable;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * The {@link #atomically} methods delegates to the {@link Callable} passed in. It catches {@link Exception} and re-throws
 * as a {@link RuntimeException} to allow the STM implementation to attempt a retry. Serious (non-recoverable/STM implementation
 * events like {@link org.multiverse.api.exceptions.ReadConflict}) extend {@link Error} so as not to be allow this kind
 * of thing to interfere with it.
 *
 * So, we shouldn't catch {@link Throwable} in the try-catch block.
 *
 * @see http://multiverse.codehaus.org/apidocs/org/multiverse/api/exceptions/ControlFlowError.html
 */
public class RunAtomically<R, E extends Exception> extends Atomic<R> {

    private final Callable<R, E> callable;
    private AtomicInteger abortions = new AtomicInteger();

    public static <R, E extends Exception> R runAtomically(Callable<R, E> callable) {
        return new RunAtomically<R, E>(callable).execute();
    }

    public RunAtomically(Callable<R, E> callable) {
        this.callable = callable;
    }

    @Override
    public R atomically() {
        try {
            return callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
