package bad.robot.pingpong.server;

import bad.robot.pingpong.UncheckedException;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class Jmx {

    public static void register(Object object) {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = createObjectName();
        try {
            if (server.isRegistered(name))
                server.unregisterMBean(name);
            server.registerMBean(object, name);
        } catch (Exception e) {
            throw new UncheckedException(e);
        }
    }

    private static ObjectName createObjectName() {
        try {
            return new ObjectName("bad.robot:type=ThreadCounter");
        } catch (MalformedObjectNameException e) {
            throw new UncheckedException(e);
        }
    }
}
