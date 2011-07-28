/*
 * Copyright (c) 2009-2011, bad robot (london) ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bad.robot.turtle.server;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

import static bad.robot.turtle.server.ServerRunningCondition.running;
import static bad.robot.turtle.server.ServerStartedCondition.started;
import static com.google.code.tempusfugit.temporal.Conditions.not;
import static com.google.code.tempusfugit.temporal.Duration.millis;
import static com.google.code.tempusfugit.temporal.Timeout.timeout;
import static com.google.code.tempusfugit.temporal.WaitFor.waitOrTimeout;

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
