package bad.robot.concordion.ant;

import java.util.*;

import static bad.robot.concordion.ant.StringComparator.Order.ASCENDING;
import static bad.robot.concordion.ant.StringComparator.Order.DESCENDING;
import static java.util.Arrays.asList;

public class GroupedCollectionOfConcordionSpecifications implements Builder<Map<String, ? extends SortedSet<? extends DuplicateAwareConcordionSpecification>>, RuntimeException> {

    private Set<ConcordionSpecification> tests = new HashSet<ConcordionSpecification>();

    public <T> GroupedCollectionOfConcordionSpecifications with(SortedSet<ConcordionSpecification> tests) {
        if (tests == null)
            throw new IllegalArgumentException();
        this.tests = tests;
        return this;
    }

    public Map<String, ? extends SortedSet<DuplicateAwareConcordionSpecification>> build() {
        Map<String, TreeSet<DuplicateAwareConcordionSpecification>> results = new TreeMap<String, TreeSet<DuplicateAwareConcordionSpecification>>(new StringComparator(DESCENDING));
        for (ConcordionSpecification test : tests) {
            boolean duplicate = false;
            for (String group : split(test.getGroup())) {
                String key = group.trim();
                createIfRequired(results, key);
                results.get(key).add(new DuplicateAwareConcordionSpecification(test, duplicate));
                duplicate = true;
            }
        }
        return sort(results, new StringComparator(ASCENDING));
    }

    private static List<String> split(String string) {
        return asList(string.split(","));
    }

    private static void createIfRequired(Map<String, TreeSet<DuplicateAwareConcordionSpecification>> results, String group) {
        if (!results.containsKey(group))
            results.put(group, new TreeSet<DuplicateAwareConcordionSpecification>(new DescrendingBySpecification()));
    }

    public static <K, V> Map<K, V> sort(Map<K, V> in, Comparator<? super K> compare) {
        Map<K, V> result = new LinkedHashMap<K, V>();
        K[] array = (K[]) in.keySet().toArray();
        Arrays.sort(array, compare);
        for (K item : array)
            result.put(item, in.get(item));
        return result;
    }

}


