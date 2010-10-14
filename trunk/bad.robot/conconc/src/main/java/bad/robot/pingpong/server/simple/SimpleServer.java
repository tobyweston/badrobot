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

package bad.robot.pingpong.server.simple;

import bad.robot.pingpong.server.Server;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;
import org.simpleframework.util.thread.Scheduler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class SimpleServer implements Server {

    private final Connection connection;
    private final Scheduler scheduler = new Scheduler(5);

    public SimpleServer() throws IOException {
        connection = new SocketConnection(new AsynchronousContainer(scheduler));
    }

    public void start() throws IOException {
        SocketAddress address = new InetSocketAddress(8080);
        connection.connect(address);
    }

    public void stop() throws IOException {
        connection.close();
        scheduler.stop();
    }

    public static void main(String... args) throws IOException {
        new SimpleServer().start();
    }

}
