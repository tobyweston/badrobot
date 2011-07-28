package bad.robot.turtle.server.rest;

import bad.robot.turtle.server.InvalidUuidException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;


@Provider
public class InvalidUuidExceptionMapper implements ExceptionMapper<InvalidUuidException> {

    @Override
    public Response toResponse(InvalidUuidException e) {
        return new PlainTextResponseBuilder().with(BAD_REQUEST).with(e.getMessage()).build();
    }

}
