package bad.robot.pingpong;

import com.google.code.tempusfugit.temporal.Duration;

public interface StopWatch {

    void start();

    void stop();

    Duration elapsedTime();
}
