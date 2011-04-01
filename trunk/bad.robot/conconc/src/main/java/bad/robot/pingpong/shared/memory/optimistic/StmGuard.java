package bad.robot.pingpong.shared.memory.optimistic;

import bad.robot.pingpong.shared.memory.pessimistic.Guard;
import com.google.code.tempusfugit.concurrency.Callable;

import static bad.robot.pingpong.shared.memory.optimistic.RunAtomically.runAtomically;

/**
 * any references accessed within the "atomic" block need to be of type {@link akka.stm.Ref} to be subject to atomicity
 * constraints. Otherwise, this class acts as a simple delegate and wont enforce any atomicity.
 */
public class StmGuard implements Guard {

    @Override
    public <R, E extends Exception> R execute(Callable<R, E> callable) throws E {
        return runAtomically(callable);
    }

    @Override
    public Boolean guarding() {
        return true;
    }

}
