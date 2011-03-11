package bad.robot.pingpong.shared.memory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

public class Executors {

    public static ExecutorService newFixedThreadPool(int threads, ThreadFactory threadFactory, ThreadPoolTimer observer) {
        return new ObservableThreadPoolExecutor(threads, threadFactory, observer);
    }

}
