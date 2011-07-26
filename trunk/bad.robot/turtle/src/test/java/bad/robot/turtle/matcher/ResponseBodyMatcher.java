package bad.robot.turtle.matcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;


import javax.ws.rs.core.Response;

class ResponseBodyMatcher extends TypeSafeMatcher<Response> {

    private final String expected;

    public ResponseBodyMatcher(String expected) {
        this.expected = expected;
    }

    @Override
    public boolean matchesSafely(Response actual) {
        return actual.getEntity().toString().contains(expected);
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(expected);
    }
}