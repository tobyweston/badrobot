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

import bad.robot.pingpong.server.simple.SimpleServer;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sun.net.www.http.HttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

public class PingIntegrationTest {

    private SimpleServer server;

    @Before
    public void start() throws IOException {
        server = new SimpleServer();
        server.start();
    }

    @Test
    public void shouldPing() throws IOException {
        HttpClient http = HttpClient.New(new URL("http://localhost:8080"));
        InputStream stream = http.getInputStream();
        OutputStream output = http.getOutputStream();
        IOUtils.write("hello", output);
        List response = IOUtils.readLines(stream);
        System.out.println(response.size());
    }
    
    @After
    public void stop() throws IOException {
        server.stop();
    }
}
