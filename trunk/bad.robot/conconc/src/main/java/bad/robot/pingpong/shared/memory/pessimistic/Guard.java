package bad.robot.pingpong.shared.memory.pessimistic;

import com.google.code.tempusfugit.concurrency.Callable;

public interface Guard {

    <R, E extends Exception> R execute(Callable<R, E> callable) throws E;

    Boolean guarding();
}
