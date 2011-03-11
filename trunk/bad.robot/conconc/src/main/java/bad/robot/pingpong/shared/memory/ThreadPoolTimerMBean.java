package bad.robot.pingpong.shared.memory;

public interface ThreadPoolTimerMBean {

    Long getMeanExecutionTime();

    void reset();
}
