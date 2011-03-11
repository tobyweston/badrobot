package bad.robot.pingpong.server;

import bad.robot.pingpong.UncheckedException;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class Jmx {

    public static void register(Object object, String name) {
        unregister(object, name);
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = createObjectName(name);
        try {
            server.registerMBean(object, objectName);
        } catch (Exception e) {
            throw new UncheckedException(e);
        }
    }

    public static void unregister(Object object, String name) {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = createObjectName(name);
        try {
            if (server.isRegistered(objectName))
                server.unregisterMBean(objectName);
        } catch (Exception e) {
            throw new UncheckedException(e);
        }
    }

    private static ObjectName createObjectName(String name) {
        try {
            return new ObjectName("bad.robot:type=" + name);
        } catch (MalformedObjectNameException e) {
            throw new UncheckedException(e);
        }
    }
}
