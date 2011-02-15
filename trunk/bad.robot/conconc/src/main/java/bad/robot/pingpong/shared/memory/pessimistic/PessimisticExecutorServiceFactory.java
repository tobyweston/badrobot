package bad.robot.pingpong.shared.memory.pessimistic;

import bad.robot.pingpong.shared.memory.ExecutorServiceFactory;
import bad.robot.pingpong.shared.memory.ObservableThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class PessimisticExecutorServiceFactory implements ExecutorServiceFactory {

    @Override
    public ExecutorService create() {
        return newFixedThreadPool(5, new ObservableThreadFactory(new ThreadCounter(new LockingGuard(new ReentrantLock()))));
    }
}
