package bad.robot.turtle.server;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

import static bad.robot.turtle.server.ServerRunningCondition.*;
import static bad.robot.turtle.server.ServerStartedCondition.*;
import static com.google.code.tempusfugit.temporal.Conditions.not;
import static com.google.code.tempusfugit.temporal.Duration.millis;
import static com.google.code.tempusfugit.temporal.Timeout.timeout;
import static com.google.code.tempusfugit.temporal.WaitFor.*;

public class TurtleServer {

    public static final int DEFAULT_PORT = 8080;

    private final static Server server = new Server(DEFAULT_PORT);


    public void start() throws Exception {
        server.setHandler(createHandler());
        server.start();
        waitOrTimeout(started(server), timeout(millis(250)));
    }

    private WebAppContext createHandler() {
        return new WebAppContext("web", "/");
    }

    public void stop() throws Exception {
        server.stop();
        waitOrTimeout(not(running(server)), timeout(millis(250)));
    }

    public static void main(String... args) throws Exception {
        new TurtleServer().start();
    }

}
