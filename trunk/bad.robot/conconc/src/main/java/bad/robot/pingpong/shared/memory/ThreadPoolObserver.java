package bad.robot.pingpong.shared.memory;

public interface ThreadPoolObserver {

    void beforeExecute(Thread thread, Runnable task);

    void afterExecute(Runnable task, Throwable throwable);

    void terminated();
}
