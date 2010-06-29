package bad.robot.concordion.ant;

import java.io.IOException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.util.Collections.emptySet;

public class SetOfConcordionSpecifications implements CollectionBuilder<SortedSet<ConcordionSpecification>, IOException> {

    private Set<IncludedFile> files = emptySet();
    private Unmarshaller<ConcordionSpecification> unmarshaller = new DefaultUnmarshaller();

    public SetOfConcordionSpecifications with(Set<IncludedFile> files) {
        this.files = files;
        return this;
    }

    public SetOfConcordionSpecifications with(Unmarshaller<ConcordionSpecification> unmarshaller) {
        this.unmarshaller = unmarshaller;
        return this;
    }

    public SortedSet<ConcordionSpecification> build() throws IOException {
        SortedSet<ConcordionSpecification> tests = new TreeSet<ConcordionSpecification>();
        for (IncludedFile file : files) {
            ConcordionSpecification html = unmarshaller.unmarshall(file);
            if (tests.contains(html))
                System.err.printf("WARNING! Concordion specification with \"%s\" as a title already exsists (%s)%n", html.getTitle(), html.getLocation());
            tests.add(html);
        }
        return tests;
    }

    private static class DefaultUnmarshaller implements Unmarshaller<ConcordionSpecification> {
        public ConcordionSpecification unmarshall(IncludedFile filename) throws IOException {
            return null;
        }
    }

}
