package bad.robot.pingpong.shared.memory;

public interface ThroughputMBean {

    Double getRequestsPerSecond();

    Long getTotalRequests();
}
