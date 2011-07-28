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

import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

public class PlainTextResponseBuilder {

    private String message = "no message";
    private Response.Status status = INTERNAL_SERVER_ERROR;

    public PlainTextResponseBuilder with(String message) {
        this.message = message;
        return this;
    }

    public PlainTextResponseBuilder with(Response.Status status) {
        this.status = status;
        return this;
    }

    public Response build() {
        Response.ResponseBuilder builder = Response.status(status).type(TEXT_PLAIN).entity(message).header("Content-Length", message.getBytes().length);
        return builder.build();
    }

}
