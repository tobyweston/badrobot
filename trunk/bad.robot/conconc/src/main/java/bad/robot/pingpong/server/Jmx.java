/*
 * Copyright (c) 2009-2011, bad robot (london) ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
