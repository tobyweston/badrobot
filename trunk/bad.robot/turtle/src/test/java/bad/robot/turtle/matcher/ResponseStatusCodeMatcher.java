package bad.robot.turtle.matcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import javax.ws.rs.core.Response;

class ResponseStatusCodeMatcher extends TypeSafeMatcher<Response> {

    private final Response.Status expected;

    public ResponseStatusCodeMatcher(Response.Status expected) {
        this.expected = expected;
    }

    @Override
    public boolean matchesSafely(Response actual) {
        return actual.getStatus() == expected.getStatusCode();
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(expected);
    }
}
