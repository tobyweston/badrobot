package bad.robot.turtle.server.rest;

import org.junit.Test;
import org.mortbay.util.ajax.JSON;

import javax.ws.rs.core.Response;

import static bad.robot.turtle.matcher.Matchers.hasHeader;
import static bad.robot.turtle.matcher.Matchers.hasStatus;
import static bad.robot.turtle.matcher.Matchers.responseBodyContains;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.core.Is.is;
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
