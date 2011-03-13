package bad.robot.pingpong.shared.memory;

public interface ThreadPoolTimerMBean {

    Long getNumberOfExecutions();

    Long getTotalTime();

    Long getMeanExecutionTime();

    Long getTerminated();

    void reset();
}
