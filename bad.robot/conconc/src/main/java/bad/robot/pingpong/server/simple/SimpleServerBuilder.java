package bad.robot.pingpong.server.simple;

import bad.robot.pingpong.server.Jmx;
import bad.robot.pingpong.shared.memory.ObservableThreadFactory;
import bad.robot.pingpong.shared.memory.ThreadObserver;
import bad.robot.pingpong.shared.memory.ThreadPoolObserver;
import bad.robot.pingpong.shared.memory.ThreadPoolTimer;

import java.io.IOException;

import static bad.robot.pingpong.shared.memory.Executors.newFixedThreadPool;
import static bad.robot.pingpong.shared.memory.pessimistic.PessimisticThreadCounters.createLockBasedThreadSafeCounter;

public class SimpleServerBuilder {

    private static final int REQUEST_PROCESSING_THREADS = 20;

    public SimpleServer build() throws IOException {
        ThreadObserver threadCounter = createLockBasedThreadSafeCounter();
        ThreadPoolTimer timer = new ThreadPoolTimer();
        registerWithJmx(threadCounter, timer);
        return new SimpleServer(newFixedThreadPool(REQUEST_PROCESSING_THREADS, new ObservableThreadFactory(threadCounter), timer));
    }

    private static void registerWithJmx(ThreadObserver threadCounter, ThreadPoolObserver timer) {
        Jmx.register(threadCounter, "ThreadCounter");
        Jmx.register(timer, "ThreadPool");
    }
}
