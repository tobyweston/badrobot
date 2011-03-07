package bad.robot.pingpong.shared.memory.pessimistic;

import com.google.code.tempusfugit.concurrency.Callable;

public class Unguarded implements Guard {

    private Unguarded() {
    }

    public static Guard unguarded() {
        return new Unguarded();
    }

    @Override
    public <R, E extends Exception> R execute(Callable<R, E> callable) throws E {
        return callable.call();
    }

    @Override
    public Boolean guarding() {
        return true;
    }
}
