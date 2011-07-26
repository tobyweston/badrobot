package bad.robot.turtle.server;

import com.google.code.tempusfugit.temporal.Condition;
import org.mortbay.jetty.Server;

public class ServerStartedCondition implements Condition {
    private final Server server;

    public static ServerStartedCondition started(Server server) {
        return new ServerStartedCondition(server);
    }

    ServerStartedCondition(Server server) {
        this.server = server;
    }

    @Override
    public boolean isSatisfied() {
        return server.isStarted();
    }
}
