package bad.robot.concordion.ant;

import java.io.IOException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.util.Collections.emptySet;

public class SetOfConcordionSpecifications implements CollectionBuilder<SortedSet<ConcordionSpecification>, IOException> {

    private Set<IncludedFile> files = emptySet();
    private final Unmarshaller<ConcordionSpecification> unmarshaller;

    public SetOfConcordionSpecifications(Unmarshaller<ConcordionSpecification> unmarshaller) {
        this.unmarshaller = unmarshaller;
    }

    public SetOfConcordionSpecifications with(Set<IncludedFile> files) {
        this.files = files;
        return this;
    }

    public SortedSet<ConcordionSpecification> build() throws IOException {
        SortedSet<ConcordionSpecification> tests = new TreeSet<ConcordionSpecification>();
        for (IncludedFile file : files) {
            ConcordionSpecification html = unmarshaller.unmarshall(file);
            if (tests.contains(html)) {
                tests.add(new ConcordionSpecification(html.getGroup(), html.getTitle() + " (duplicated test title)", html.getLocation(), html.isIgnore()));
            } else
                tests.add(html);
        }
        return tests;
    }

}
