package bad.robot.pingpong.shared.memory;

import com.google.code.tempusfugit.temporal.Duration;

import java.util.Date;

public final class MovableClock implements Clock {

    private final Date now;

    public MovableClock() {
        now = new Date(0);
    }

    public MovableClock(Date date) {
        now = new Date(date.getTime());
    }

    @Override
    public Date time() {
        return new Date(now.getTime());
    }

    public void incrementBy(Duration time) {
        now.setTime(now.getTime() + time.inMillis());
    }

}
