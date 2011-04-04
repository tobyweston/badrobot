package bad.robot.pingpong.shared.memory;

import com.google.code.tempusfugit.temporal.Duration;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadLocalMovableClock implements Clock {

    private final ThreadLocal<Date> now;
    private final static AtomicInteger count = new AtomicInteger();

    public ThreadLocalMovableClock() {
        now = new ThreadLocal<Date>() {
            @Override
            protected Date initialValue() {
                count.getAndIncrement();
                return new Date(0);
            }
        };
    }

    @Override
    public Date time() {
        Date date = now.get();
        return new Date(date.getTime());
    }

    public void incrementBy(Duration time) {
        now.get().setTime(now.get().getTime() + time.inMillis());
    }

}
