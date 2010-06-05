package bad.robot.concordion.ant;

import java.util.*;

import static java.util.Arrays.asList;

public class TestHtmlMapBuilder implements Builder<Map<String, ? extends SortedSet<DuplicateAwareTestHtml>>, RuntimeException>{

    private Set<TestHtml> tests = new HashSet<TestHtml>();

    public <T> TestHtmlMapBuilder with(SortedSet<TestHtml> tests) {
        if (tests == null)
            throw new IllegalArgumentException();
        this.tests = tests;
        return this;
    }

    @Override
    public Map<String, ? extends SortedSet<DuplicateAwareTestHtml>> build() {
        Map<String, TreeSet<DuplicateAwareTestHtml>> results = new HashMap<String, TreeSet<DuplicateAwareTestHtml>>();
        for (TestHtml test : tests) {
            boolean duplicate = false;
            for (String iteration : split(test.getIteration())) {
                String key = iteration.trim();
                createIfRequired(results, key);
                results.get(key).add(new DuplicateAwareTestHtml(test, duplicate));
                duplicate = true;
            }
        }
        return results;
    }

    private static List<String> split(String string) {
        return asList(string.split(","));
    }

    private void createIfRequired(Map<String, TreeSet<DuplicateAwareTestHtml>> results, String iteration) {
        if (!results.containsKey(iteration))
            results.put(iteration, new TreeSet<DuplicateAwareTestHtml>());
    }

}


