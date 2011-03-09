package bad.robot.pingpong.shared.memory;

public interface ThreadCounterMBean {

    Long getActiveThreads();

    Long getCreatedThreads();

    void reset();

}
