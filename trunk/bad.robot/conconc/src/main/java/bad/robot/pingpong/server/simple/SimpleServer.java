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

package bad.robot.pingpong.server.simple;

import bad.robot.pingpong.UncheckedException;
import bad.robot.pingpong.server.Server;
import org.simpleframework.http.Address;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.resource.Resource;
import org.simpleframework.http.resource.ResourceContainer;
import org.simpleframework.http.resource.ResourceEngine;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;

import static bad.robot.pingpong.server.simple.StandardResponseHeader.standardResponseHeaders;
import static bad.robot.pingpong.transport.ResponseCode.NOT_FOUND;

public class SimpleServer implements Server {

    private final Connection connection;
    private final ExecutorService threads;

    public SimpleServer(ExecutorService threads) throws IOException {
        this.threads = threads;
        connection = new SocketConnection(new AsynchronousContainer(threads, new ResourceContainer(new ResourceEngine() {
            @Override
            public Resource resolve(Address target) {
                if (target.getPath().getPath().equals("/pingpong"))
                    return new PingResource();
                return new NotFoundResource();
            }
        })));
    }

    public void start() throws IOException {
        connection.connect(new InetSocketAddress(8080));
    }

    public void stop() throws IOException {
        connection.close();
        threads.shutdown();
    }

    public static void main(String... args) throws IOException {
        new SimpleServerBuilder().build().start();
    }

    private static class NotFoundResource implements Resource {
        @Override
        public void handle(Request request, Response response) {
            try {
                standardResponseHeaders().setOn(response, NOT_FOUND);
                response.close();
            } catch (IOException e) {
                throw new UncheckedException();
            }
        }
    }

    private static class PingResource implements Resource {
        @Override
        public void handle(Request request, Response response) {
            new Ping().handle(request, response);
        }
    }

}
