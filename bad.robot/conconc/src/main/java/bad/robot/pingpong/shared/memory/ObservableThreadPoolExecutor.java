package bad.robot.pingpong.shared.memory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

class ObservableThreadPoolExecutor extends ThreadPoolExecutor  {

    private final ThreadPoolObserver observer;

    public ObservableThreadPoolExecutor(int threads, ThreadFactory factory, ThreadPoolObserver observer) {
        super(threads, threads, 0L, MILLISECONDS, new LinkedBlockingQueue<Runnable>(), factory);
        this.observer = observer;
    }

    @Override
    protected void beforeExecute(Thread thread, Runnable task) {
        observer.beforeExecute(thread, task);
    }

    @Override
    protected void afterExecute(Runnable task, Throwable throwable) {
        observer.afterExecute(task, throwable);
    }

    @Override
    protected void terminated() {
        observer.terminated();
    }
}
