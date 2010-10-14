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

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class SimpleServer implements Server {

    private Connection connection;

    public SimpleServer() throws IOException {
        connection = new SocketConnection(new HelloWorld());
    }

    public void start() throws IOException {
        SocketAddress address = new InetSocketAddress(8080);
        connection.connect(address);
    }

    public void stop() throws IOException {
        connection.close();
    }

    public static class HelloWorld implements Container {

        public void handle(Request request, Response response) {
            PrintStream body = null;
            try {
                body = response.getPrintStream();
                long time = System.currentTimeMillis();

                response.set("Content-Type", "text/plain");
                response.set("Server", "HelloWorld/1.0 (Simple 4.0)");
                response.setDate("Date", time);
                response.setDate("Last-Modified", time);

                body.println("Hello World");

            }
            catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (body != null)
                    body.close();
            }
        }
    }

    public static void main(String... args) throws IOException {
        new SimpleServer().start();
    }
}
