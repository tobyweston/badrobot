package bad.robot.concordion.ant;

import org.junit.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GroupedCollectionOfConcordionSpecificationsTest {

    private static final boolean DONT_IGNORE = false;

    private static final DuplicateAwareConcordionSpecification TEST_1 = new DuplicateAwareConcordionSpecification(new ConcordionSpecification("1", "title", "location", DONT_IGNORE));
    private static final DuplicateAwareConcordionSpecification TEST_2 = new DuplicateAwareConcordionSpecification(new ConcordionSpecification("2", "titlex", "locationx", DONT_IGNORE));
    private static final DuplicateAwareConcordionSpecification TEST_3 = new DuplicateAwareConcordionSpecification(new ConcordionSpecification("1, 2", "titley", "locationy", DONT_IGNORE));
    private static final DuplicateAwareConcordionSpecification TEST_A = new DuplicateAwareConcordionSpecification(new ConcordionSpecification("A", "titleA", "location", DONT_IGNORE));
    private static final DuplicateAwareConcordionSpecification TEST_B = new DuplicateAwareConcordionSpecification(new ConcordionSpecification("B", "titleB", "location", DONT_IGNORE));
    private static final DuplicateAwareConcordionSpecification TEST_C = new DuplicateAwareConcordionSpecification(new ConcordionSpecification("C", "titleC", "location", DONT_IGNORE));
    private static final DuplicateAwareConcordionSpecification TEST_D = new DuplicateAwareConcordionSpecification(new ConcordionSpecification("D, A", "titleD", "location", DONT_IGNORE));
    private static final DuplicateAwareConcordionSpecification TEST_E = new DuplicateAwareConcordionSpecification(new ConcordionSpecification("A", "xbvc", "location", DONT_IGNORE));

    private final GroupedCollectionOfConcordionSpecifications builder = new GroupedCollectionOfConcordionSpecifications();

    @Test (expected = IllegalArgumentException.class)
    public void shouldBuildWithNullSet() {
        assertThat(builder.with(null).build().isEmpty(), is(true));
    }

    @Test
    public void shouldBuildWithDefaultSet() {
        assertThat(builder.build().isEmpty(), is(true));
    }

    @Test
    public void shouldBuildWithEmptySet() {
        SortedSet<ConcordionSpecification> emptySet = new TreeSet<ConcordionSpecification>();
        assertThat(builder.with(emptySet).build().isEmpty(), is(true));
    }

    @Test
    public void shouldBuildResults() {
        Map<String, ? extends SortedSet<DuplicateAwareConcordionSpecification>> result = builder.with(set(TEST_1)).build();
        assertThat(result.size(), is(1));
        assertThat(result.get("1"), hasItem(TEST_1));
    }

    @Test
    public void shouldBuildMultipleGroupsResults() {
        ConcordionSpecification html = new ConcordionSpecification("1, 2", "title", "location", false);
        DuplicateAwareConcordionSpecification test = new DuplicateAwareConcordionSpecification(html);
        Map<String, ? extends SortedSet<DuplicateAwareConcordionSpecification>> result = builder.with(set(test)).build();
        assertThat(result.size(), is(2));
        assertThat(result.get("1"), hasItem(new DuplicateAwareConcordionSpecification(test, false)));
        assertThat(result.get("2"), hasItem(new DuplicateAwareConcordionSpecification(test, true)));
    }

    @Test
    public void shouldBuildMultipleFilesResults() {
        Map<String, ? extends SortedSet<DuplicateAwareConcordionSpecification>> result = builder.with(set(TEST_1, TEST_2)).build();
        assertThat(result.size(), is(2));
        assertThat(result.get("1"), hasItem(TEST_1));
        assertThat(result.get("2"), hasItem(TEST_2));
    }

    @Test
    public void shouldDetectDuplicates() {
        Map<String, ? extends SortedSet<DuplicateAwareConcordionSpecification>> result = builder.with(set(TEST_3)).build();
        assertThat(result.size(), is(2));
        assertThat(result.get("1").first().isDuplicate(), is(false));
        assertThat(result.get("2").first().isDuplicate(), is(true));
    }

    @Test
    public void shouldOrderGroups() {
        Map<String, ? extends SortedSet<DuplicateAwareConcordionSpecification>> result = builder.with(set(TEST_D, TEST_B, TEST_C, TEST_A)).build();
        String[] group = result.keySet().toArray(new String[]{});
        assertThat(Arrays.toString(group), group[0], is("A"));
        assertThat(Arrays.toString(group), group[1], is("B"));
        assertThat(Arrays.toString(group), group[2], is("C"));
        assertThat(Arrays.toString(group), group[3], is("D"));
    }

    @Test
    public void shouldOrderTestsWithinGroup() {
        DuplicateAwareConcordionSpecification[] results = getContentAsArray(builder.with(set(TEST_E, TEST_D, TEST_A)).build(), TEST_A);
        assertThat(results[0].getTitle(), is(TEST_A.getTitle()));
        assertThat(results[1].getTitle(), is(TEST_D.getTitle()));
        assertThat(results[2].getTitle(), is(TEST_E.getTitle()));
    }

    private static DuplicateAwareConcordionSpecification[] getContentAsArray(Map<String, ? extends SortedSet<DuplicateAwareConcordionSpecification>> result, DuplicateAwareConcordionSpecification specificaiton) {
        SortedSet<DuplicateAwareConcordionSpecification> A = result.get(specificaiton.getGroup());
        DuplicateAwareConcordionSpecification[] results = A.toArray(new DuplicateAwareConcordionSpecification[]{});
        return results;
    }

    private static TreeSet<ConcordionSpecification> set(ConcordionSpecification... test) {
        return new TreeSet<ConcordionSpecification>(asList(test));
    }
}
