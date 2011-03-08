package bad.robot.pingpong.shared.memory.optimistic;

import bad.robot.pingpong.shared.memory.pessimistic.Guard;
import com.google.code.tempusfugit.concurrency.Callable;

import static bad.robot.pingpong.shared.memory.optimistic.RunAtomically.runAtomically;

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
