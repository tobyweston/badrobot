package bad.robot.turtle.matcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import javax.ws.rs.core.Response;
import java.util.List;

class ResponseHeaderMatcher extends TypeSafeMatcher<Response> {
    private final String header;
    private final String expected;

    public ResponseHeaderMatcher(String header, String expected) {
        this.header = header;
        this.expected = expected;
    }

    @Override
    public boolean matchesSafely(Response actual) {
        List<Object> objects = actual.getMetadata().get(header);
        if (objects == null)
            return false;
        for (Object object : objects)
            if (object.toString().equals(expected))
                return true;
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("header ").appendValue(header).appendText(" containing ").appendValue(expected);
    }
}
