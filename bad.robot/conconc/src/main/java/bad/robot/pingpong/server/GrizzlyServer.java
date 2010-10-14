/*
 * Copyright (c) 2009-2010, bad robot (london) ltd
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

import com.sun.grizzly.http.Management;
import com.sun.grizzly.http.embed.GrizzlyWebServer;
import com.sun.grizzly.tcp.http11.GrizzlyAdapter;
import com.sun.grizzly.tcp.http11.GrizzlyRequest;
import com.sun.grizzly.tcp.http11.GrizzlyResponse;
import org.apache.commons.modeler.Registry;

import javax.management.ObjectName;
import java.io.IOException;

import static java.lang.String.format;

public class GrizzlyServer implements Server {

    private final GrizzlyWebServer server = new GrizzlyWebServer(8080, "pingpong");

    public GrizzlyServer() {
        server.addGrizzlyAdapter(new HelloWorld(), new String[]{"/"});
        server.enableJMX(new JmxManager());
        server.getStatistics().startGatheringStatistics();
        
    }

    public void start() throws IOException {
        server.start();
    }

    public void stop() {
        server.stop();
    }

    public static void main(String[] args) throws IOException {
        new GrizzlyServer().start();
    }

    private static class JmxManager implements Management {
        public void registerComponent(Object bean, ObjectName objectName, String type) throws Exception {
            Registry.getRegistry().registerComponent(bean, objectName, type);
        }

        public void unregisterComponent(ObjectName objectName) throws Exception {
            Registry.getRegistry().unregisterComponent(objectName);
        }
    }

    private static class HelloWorld extends GrizzlyAdapter {
        @Override
        public void service(GrizzlyRequest request, GrizzlyResponse response) {
            try {
                response.getWriter().println(format("hello world (%s)", request.getMethod()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
