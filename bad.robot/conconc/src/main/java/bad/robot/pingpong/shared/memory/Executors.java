package bad.robot.pingpong.shared.memory;

import bad.robot.pingpong.shared.memory.pessimistic.ThreadLocalStopWatch;
import com.google.code.tempusfugit.FactoryException;
import com.google.code.tempusfugit.temporal.Clock;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

public class Executors {

    public static ExecutorService newFixedThreadPool(int threads, ThreadFactory threadFactory) {
        return new TimedThreadPoolExecutor(threads, threadFactory, new RequestTimer(new ThreadLocalStopWatch(new Clock() {
            @Override
            public Date create() throws FactoryException {
                return new Date();
            }
        })));
    }

}
