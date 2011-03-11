package bad.robot.pingpong.shared.memory;

import bad.robot.pingpong.StopWatch;
import com.google.code.tempusfugit.temporal.Duration;

public class RequestTimer implements RequestObserver {

    private final StopWatch timer;

    public RequestTimer(StopWatch timer) {
        this.timer = timer;
    }

    @Override
    public Request started() {
        timer.start();
        return new Request() {
            @Override
            public Duration finished() {
                timer.stop();
                return timer.elapsedTime();
            }
        };
    }
}
