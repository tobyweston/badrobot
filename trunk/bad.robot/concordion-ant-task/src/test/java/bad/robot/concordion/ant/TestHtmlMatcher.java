package bad.robot.concordion.ant;

import org.hamcrest.Description;
import org.junit.internal.matchers.TypeSafeMatcher;

import java.util.List;

public class TestHtmlMatcher extends TypeSafeMatcher<List<DuplicateAwareTestHtml>> {
    private final List<DuplicateAwareTestHtml> expected;

    public static TestHtmlMatcher containsAll(List<DuplicateAwareTestHtml> expected) {
        return new TestHtmlMatcher(expected);
    }

    public TestHtmlMatcher(List<DuplicateAwareTestHtml> expected) {
        this.expected = expected;
    }

    @Override
    public boolean matchesSafely(List<DuplicateAwareTestHtml> actual) {
        if (expected.size() != actual.size())
            return false;
        for (DuplicateAwareTestHtml html : expected)            
            if (!(actual.contains(html)))
                return false;
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(expected);
    }

}
