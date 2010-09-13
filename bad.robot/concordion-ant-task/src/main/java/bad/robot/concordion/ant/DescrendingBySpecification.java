package bad.robot.concordion.ant;

import java.util.Comparator;

public class DescrendingBySpecification implements Comparator<DuplicateAwareConcordionSpecification> {
    
    public int compare(DuplicateAwareConcordionSpecification first, DuplicateAwareConcordionSpecification second) {
        return first.getTitle().compareTo(second.getTitle());
    }
}
