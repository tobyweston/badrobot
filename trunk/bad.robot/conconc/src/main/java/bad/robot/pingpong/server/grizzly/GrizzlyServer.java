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

package bad.robot.pingpong.server.grizzly;

import bad.robot.pingpong.server.Server;
import com.sun.grizzly.http.embed.GrizzlyWebServer;

import java.io.IOException;

public class GrizzlyServer implements Server {

    private final GrizzlyWebServer server = new GrizzlyWebServer(8080, "pingpong");

    public GrizzlyServer() {
        server.addGrizzlyAdapter(new Ping(), new String[]{"/"});
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

}
