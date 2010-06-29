package bad.robot.concordion.ant;

import java.util.*;

import static java.util.Arrays.asList;

public class GroupedCollectionOfConcordionSpecifications implements Builder<Map<String, ? extends SortedSet<? extends DuplicateAwareConcordionSpecification>>, RuntimeException>{

    private Set<ConcordionSpecification> tests = new HashSet<ConcordionSpecification>();

    public <T> GroupedCollectionOfConcordionSpecifications with(SortedSet<ConcordionSpecification> tests) {
        if (tests == null)
            throw new IllegalArgumentException();
        this.tests = tests;
        return this;
    }

    public Map<String, ? extends SortedSet<DuplicateAwareConcordionSpecification>> build() {
        Map<String, TreeSet<DuplicateAwareConcordionSpecification>> results = new HashMap<String, TreeSet<DuplicateAwareConcordionSpecification>>();
        for (ConcordionSpecification test : tests) {
            boolean duplicate = false;
            for (String group : split(test.getGroup())) {
                String key = group.trim();
                createIfRequired(results, key);
                results.get(key).add(new DuplicateAwareConcordionSpecification(test, duplicate));
                duplicate = true;
            }
        }
        return results;
    }

    private static List<String> split(String string) {
        return asList(string.split(","));
    }

    private void createIfRequired(Map<String, TreeSet<DuplicateAwareConcordionSpecification>> results, String group) {
        if (!results.containsKey(group))
            results.put(group, new TreeSet<DuplicateAwareConcordionSpecification>());
    }

}


