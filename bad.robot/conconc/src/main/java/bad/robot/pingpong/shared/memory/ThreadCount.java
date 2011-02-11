package bad.robot.pingpong.shared.memory;

public interface ThreadCount {

    long getActiveCount();

    long getCreatedCount();

    void reset();
}
