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
public class SetOfConcordionSpecificationsTest {

    private final Mockery context = new Mockery();

    private final Unmarshaller<ConcordionSpecification> unmarshaller = context.mock(Unmarshaller.class);
    private final SetOfConcordionSpecifications builder = new SetOfConcordionSpecifications(unmarshaller);

    @Test
    public void shouldUnmarshallWhenNoSetIsSupplied() throws IOException {
        Set<ConcordionSpecification> set = builder.build();
        assertThat(set.size(), is(0));
    }

    @Test
    public void shouldCallUnmarshaller() throws IOException {
        context.checking(new Expectations(){{
            one(unmarshaller).unmarshall(testFile("whatever")); will(returnValue(new ConcordionSpecification("1", "title", "location", false)));
            one(unmarshaller).unmarshall(testFile("trevor")); will(returnValue(new ConcordionSpecification("1", "title", "location", false)));
        }});
        builder.with(set("whatever", "trevor")).build();
    }

    @Test
    public void shouldUnmarshall() throws IOException {
        final ConcordionSpecification first = new ConcordionSpecification("1", "first", "foo", false);
        final ConcordionSpecification second = new ConcordionSpecification("2", "second", "bar", false);
        context.checking(new Expectations(){{
            one(unmarshaller).unmarshall(testFile("whatever")); will(returnValue(first));
            one(unmarshaller).unmarshall(testFile("trevor")); will(returnValue(second));
        }});
        Set<ConcordionSpecification> set = builder.with(set("whatever", "trevor")).build();
        assertThat(set.size(), is(2));
        assertThat(set, hasItem(first));
        assertThat(set, hasItem(second));
    }

    private static IncludedFile testFile(String filename) {
        return new IncludedFile(dummyFile(), filename);
    }

    private Set<IncludedFile> set(String... filenames) {
        Set<IncludedFile> set = new HashSet<IncludedFile>();
        for (String filename : filenames)
            set.add(new IncludedFile(dummyFile(), filename));
        return set;
    }

    private static File dummyFile() {
        return new File(".");
    }
}
