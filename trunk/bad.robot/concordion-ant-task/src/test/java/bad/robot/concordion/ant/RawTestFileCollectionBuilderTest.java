package bad.robot.concordion.ant;

import org.apache.tools.ant.FileScanner;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.Set;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JMock.class)
public class RawTestFileCollectionBuilderTest {

    private final Mockery context = new Mockery();

    private static final RawTestFile TEST_FILE_1 = new RawTestFile(new File("."), "file1");
    private static final RawTestFile TEST_FILE_2 = new RawTestFile(new File("."), "file2");

    private final FileScanner scanner = context.mock(FileScanner.class);
    private final RawTestFileCollectionBuilder builder = new RawTestFileCollectionBuilder();

    @Test
    public void shouldBuildCollection() {
        context.checking(new Expectations(){{
            atLeast(1).of(scanner).getBasedir(); will(returnValue(new File(".")));
            one(scanner).getIncludedFiles(); will(returnValue(new String[] {"file1", "file2"}));
        }});
        Set<RawTestFile> set = builder.with(scanner).build();
        assertThat(set.size(), is(2));
        assertThat(set, hasItem(TEST_FILE_1));
        assertThat(set, hasItem(TEST_FILE_2));
    }

    @Test
    public void shouldBuildEmptyCollectionWhenScannerNotSet() {
        assertThat(builder.build().size(), is(0));
    }

}
