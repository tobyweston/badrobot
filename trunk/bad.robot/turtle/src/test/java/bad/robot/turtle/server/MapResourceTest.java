package bad.robot.turtle.server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MapResourceTest {

    private final TurtleServer server = new TurtleServer();

    @Before
    public void startServer() throws Exception {
        server.start();
    }

    @After
    public void stopServer() throws Exception {
        server.stop();
    }

    @Test
    public void invalidUuid() {
        //
    }
}
