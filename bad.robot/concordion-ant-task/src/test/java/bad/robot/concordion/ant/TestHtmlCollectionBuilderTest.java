package bad.robot.concordion.ant;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(JMock.class)
public class TestHtmlCollectionBuilderTest {

    private final Mockery context = new Mockery();

    private final TestHtmlCollectionBuilder builder = new TestHtmlCollectionBuilder();
    private final Unmarshaller<TestHtml> unmarshaller = context.mock(Unmarshaller.class);

    @Test
    public void emptyFiles() throws IOException {
        Set<TestHtml> set = builder.with(unmarshaller).build();
        assertThat(set.size(), is(0));
    }

    @Test
    public void shouldCallUnmarshaller() throws IOException {
        context.checking(new Expectations(){{
            one(unmarshaller).unmarshall(testFile("whatever")); will(returnValue(new TestHtml("1", "title", "location", false)));
            one(unmarshaller).unmarshall(testFile("trevor")); will(returnValue(new TestHtml("1", "title", "location", false)));
        }});
        builder.with(unmarshaller).with(set("whatever", "trevor")).build();
    }

    @Test
    public void shouldUnmarshall() throws IOException {
        final TestHtml first = new TestHtml("1", "first", "foo", false);
        final TestHtml second = new TestHtml("2", "second", "bar", false);
        context.checking(new Expectations(){{
            one(unmarshaller).unmarshall(testFile("whatever")); will(returnValue(first));
            one(unmarshaller).unmarshall(testFile("trevor")); will(returnValue(second));
        }});
        Set<TestHtml> set = builder.with(unmarshaller).with(set("whatever", "trevor")).build();
        assertThat(set.size(), is(2));
        assertThat(set, hasItem(first));
        assertThat(set, hasItem(second));
    }

    private static RawTestFile testFile(String filename) {
        return new RawTestFile(dummyFile(), filename);
    }

    private Set<RawTestFile> set(String... filenames) {
        Set<RawTestFile> set = new HashSet<RawTestFile>();
        for (String filename : filenames)
            set.add(new RawTestFile(dummyFile(), filename));
        return set;
    }

    private static File dummyFile() {
        return new File(".");
    }
}
