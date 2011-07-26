package bad.robot.turtle.server;

import com.google.code.tempusfugit.temporal.Condition;
import org.mortbay.jetty.Server;

public class ServerRunningCondition implements Condition {
    private final Server server;

    public static ServerRunningCondition running(Server server) {
        return new ServerRunningCondition(server);
    }

    private ServerRunningCondition(Server server) {
        this.server = server;
    }

    @Override
    public boolean isSatisfied() {
        return server.isRunning();
    }
}
