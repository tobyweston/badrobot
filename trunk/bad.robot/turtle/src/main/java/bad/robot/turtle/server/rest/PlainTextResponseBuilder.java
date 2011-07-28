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
