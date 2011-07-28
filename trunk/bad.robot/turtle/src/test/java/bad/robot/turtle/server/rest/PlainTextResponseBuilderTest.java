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

package bad.robot.turtle.server.rest;

import org.junit.Test;

import javax.ws.rs.core.Response;

import static bad.robot.turtle.matcher.Matchers.*;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static org.junit.Assert.assertThat;

public class PlainTextResponseBuilderTest {

    @Test
    public void shouldBuildValidResponse() {
        verify(new PlainTextResponseBuilder().with("foo").with(BAD_REQUEST).build(), "foo", BAD_REQUEST);
    }

    @Test
    public void shouldUseDefaultForNoMessage() {
        verify(new PlainTextResponseBuilder().with(BAD_REQUEST).build(), "no message", BAD_REQUEST);
    }

    @Test
    public void shouldUseDefaultForNoStatusCode() {
        verify(new PlainTextResponseBuilder().with("foo").build(), "foo", INTERNAL_SERVER_ERROR);
    }

    private static void verify(Response response, String expectedMessage, Response.Status expectedStatus) {
        assertThat(response, hasStatus(expectedStatus));
        assertThat(response, responseBodyContains(expectedMessage));
        assertThat(response, hasHeader("Content-Type", "text/plain"));
        assertThat(response, hasHeader("Content-Length", String.valueOf(expectedMessage.getBytes().length)));
    }

}
