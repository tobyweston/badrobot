package bad.robot.pingpong.shared.memory;

import com.google.code.tempusfugit.temporal.Duration;

public interface RequestObserver {

    Request started();

    public interface Request {
        Duration finished();
    }
}
