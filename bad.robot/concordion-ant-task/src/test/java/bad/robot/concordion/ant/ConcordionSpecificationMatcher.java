package bad.robot.concordion.ant;

import org.hamcrest.Description;
import org.junit.internal.matchers.TypeSafeMatcher;

import java.util.List;

public class ConcordionSpecificationMatcher extends TypeSafeMatcher<List<DuplicateAwareConcordionSpecification>> {

    private final List<DuplicateAwareConcordionSpecification> expected;

    public static ConcordionSpecificationMatcher containsAll(List<DuplicateAwareConcordionSpecification> expected) {
        return new ConcordionSpecificationMatcher(expected);
    }

    public ConcordionSpecificationMatcher(List<DuplicateAwareConcordionSpecification> expected) {
        this.expected = expected;
    }

    @Override
    public boolean matchesSafely(List<DuplicateAwareConcordionSpecification> actual) {
        if (expected.size() != actual.size())
            return false;
        for (DuplicateAwareConcordionSpecification html : expected)
            if (!(actual.contains(html)))
                return false;
        return true;
    }

    public void describeTo(Description description) {
        description.appendValue(expected);
    }

}
