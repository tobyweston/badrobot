package bad.robot.turtle.server.rest;

import bad.robot.turtle.server.InvalidUuidException;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class InvalidUuidExceptionMapperTest {

    @Test
    public void shouldHandleEntityNotFound() {
        Response response = new InvalidUuidExceptionMapper().toResponse(new InvalidUuidException("cheese"));
        assertThat(response.getStatus(), is(400));
        assertThat(response.getEntity().toString(), is("cheese"));
    }

}
