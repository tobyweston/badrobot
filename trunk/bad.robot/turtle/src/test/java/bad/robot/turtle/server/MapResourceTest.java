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

import bad.robot.http.HttpClient;
import bad.robot.http.HttpResponse;
import bad.robot.http.apache.ApacheHttpClient;
import bad.robot.http.apache.ApacheHttpClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static bad.robot.turtle.http.Matchers.hasBody;
import static bad.robot.turtle.http.Matchers.hasStatus;
import static org.junit.Assert.assertThat;

public class MapResourceTest {

    private final TurtleServer server = new TurtleServer();

    @Before
    public void startServer() throws Exception {
        server.start();
    }

    @After
    public void stopServer() throws Exception {
        server.stop();
    }

    @Test
    public void invalidUuid() throws MalformedURLException {
        HttpClient http = new ApacheHttpClient(new ApacheHttpClientBuilder());
        HttpResponse response = http.get(new URL("http://localhost:8080/map/cheese"));
        assertThat(response, hasStatus(400));
        assertThat(response, hasBody("Invalid UUID string: cheese"));
    }

}
