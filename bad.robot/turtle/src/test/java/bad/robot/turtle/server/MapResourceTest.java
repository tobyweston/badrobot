package bad.robot.turtle.server;

import bad.robot.http.HttpClient;
import bad.robot.http.HttpResponse;
import bad.robot.http.apache.ApacheHttpClient;
import bad.robot.http.apache.ApacheHttpClientBuilder;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertThat;

public class MapResourceTest {

    private final TurtleServer server = new TurtleServer();

    @Before
    public void startServer() throws Exception {
        server.start();
    }

    @After
    public void stopServer() throws Exception {
        server.stop();
    }

    @Test
    public void invalidUuid() throws MalformedURLException {
        HttpClient http = new ApacheHttpClient(new ApacheHttpClientBuilder());
        HttpResponse response = http.get(new URL("http://localhost:8080/map/cheese"));
        assertThat(response, HttpResponseStatusCodeMatcher.hasStatus(400));
        assertThat(response, HttpResponseBodyMatcher.hasBody("Invalid UUID string: cheese"));
    }

    private static class HttpResponseStatusCodeMatcher extends TypeSafeMatcher<HttpResponse> {
        private final int expected;

        private HttpResponseStatusCodeMatcher(int expected) {
            this.expected = expected;
        }

        public static HttpResponseStatusCodeMatcher hasStatus(int expected) {
            return new HttpResponseStatusCodeMatcher(expected);
        }

        @Override
        public boolean matchesSafely(HttpResponse actual) {
            return actual.getStatusCode() == expected;
        }

        @Override
        public void describeTo(Description description) {
            description.appendValue(expected);
        }
    }

    private static class HttpResponseBodyMatcher extends TypeSafeMatcher<HttpResponse> {
        private final String expected;

        private HttpResponseBodyMatcher(String expected) {
            this.expected = expected;
        }

        public static HttpResponseBodyMatcher hasBody(String expected) {
            return new HttpResponseBodyMatcher(expected);
        }

        @Override
        public boolean matchesSafely(HttpResponse actual) {
            return actual.getContent().equals(expected);
        }

        @Override
        public void describeTo(Description description) {
            description.appendValue(expected);
        }
    }
}
