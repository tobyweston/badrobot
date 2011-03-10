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

import bad.robot.pingpong.server.simple.SimpleServer;
import bad.robot.pingpong.shared.memory.ObservableThreadFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Executors;

import static bad.robot.pingpong.Matchers.hasContent;
import static bad.robot.pingpong.Matchers.hasStatus;
import static bad.robot.pingpong.shared.memory.pessimistic.PessimisticThreadCounters.createLockBasedThreadSafeCounter;
import static bad.robot.pingpong.transport.ResponseCode.NOT_FOUND;
import static bad.robot.pingpong.transport.ResponseCode.OK;
import static org.junit.Assert.assertThat;

public class PingIntegrationTest {

    private SimpleServer server;

    @Before
    public void start() throws IOException {
        server = new SimpleServer(Executors.newFixedThreadPool(5, new ObservableThreadFactory(createLockBasedThreadSafeCounter())));
        server.start();
    }

    @Test
    public void shouldPong() throws IOException, URISyntaxException {
        HttpGet get = new HttpGet(new URI("http://localhost:8080/pingpong"));
        HttpClient http = new DefaultHttpClient();
        HttpResponse response = http.execute(get);
        assertThat(response, hasStatus(OK));
        assertThat(response, hasContent("pong"));
    }

    @Test
    public void shouldReturn404NotFound() throws IOException, URISyntaxException {
        HttpGet get = new HttpGet(new URI("http://localhost:8080/not/found"));
        HttpClient http = new DefaultHttpClient();
        HttpResponse response = http.execute(get);
        assertThat(response, hasStatus(NOT_FOUND));
    }

    @After
    public void stop() throws IOException {
        server.stop();
    }

}
