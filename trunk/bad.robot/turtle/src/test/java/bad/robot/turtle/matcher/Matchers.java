package bad.robot.turtle.matcher;


import org.hamcrest.TypeSafeMatcher;

import javax.ws.rs.core.Response;

public class Matchers {

    public static TypeSafeMatcher<Response> hasStatus(Response.Status status) {
        return new ResponseStatusCodeMatcher(status);
    }

    public static TypeSafeMatcher<Response> responseBodyContains(String string) {
        return new ResponseBodyMatcher(string);
    }

    public static TypeSafeMatcher<Response> hasHeader(String header, String expected) {
        return new ResponseHeaderMatcher(header, expected);
    }
}
