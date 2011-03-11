package bad.robot.pingpong.shared.memory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

class TimedThreadPoolExecutor extends ThreadPoolExecutor implements TimedThreadPoolExecutorMBean {

    private final RequestObserver timer;
    private final AtomicLong totalTime = new AtomicLong(0);
    private final AtomicLong tasks = new AtomicLong(0);
    private RequestObserver.Request request;

    public TimedThreadPoolExecutor(int threads, ThreadFactory factory, RequestObserver timer) {
        super(threads, threads, 0L, MILLISECONDS, new LinkedBlockingQueue<Runnable>(), factory);
        this.timer = timer;
    }

    // need map to capture requests (Map<Runnable, Request>)
    @Override
    protected void beforeExecute(Thread thread, Runnable task) {
        request = timer.started();
        tasks.incrementAndGet();
    }

    @Override
    protected void afterExecute(Runnable runnable, Throwable throwable) {
        if (request != null)
            totalTime.addAndGet(request.finished().inMillis());
    }

    @Override
    public Long getMeanExecutionTime() {
        System.out.println("tasks : " + tasks.get() + " over " + totalTime.get() +"ms");
        return totalTime.get() / tasks.get();
    }
}
