package bad.robot.concordion.ant;

import java.io.IOException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.util.Collections.emptySet;

public class TestHtmlCollectionBuilder implements CollectionBuilder<SortedSet<TestHtml>, IOException> {

    private Set<RawTestFile> files = emptySet();
    private Unmarshaller<TestHtml> unmarshaller = new DefaultUnmarshaller();

    public TestHtmlCollectionBuilder with(Set<RawTestFile> files) {
        this.files = files;
        return this;
    }

    public TestHtmlCollectionBuilder with(Unmarshaller<TestHtml> unmarshaller) {
        this.unmarshaller = unmarshaller;
        return this;
    }

    @Override
    public SortedSet<TestHtml> build() throws IOException {
        SortedSet<TestHtml> tests = new TreeSet<TestHtml>();
        for (RawTestFile file : files) {
            tests.add(unmarshaller.unmarshall(file));
        }
        return tests;
    }

    private static class DefaultUnmarshaller implements Unmarshaller<TestHtml> {
        @Override
        public TestHtml unmarshall(RawTestFile filename) throws IOException {
            return null;
        }
    }

}
