package bad.robot.concordion.ant;

import org.junit.Test;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GroupedCollectionOfConcordionSpecificationsTest {

    private static final DuplicateAwareConcordionSpecification TEST_1 = new DuplicateAwareConcordionSpecification(new ConcordionSpecification("1", "title", "location", false));
    private static final DuplicateAwareConcordionSpecification TEST_2 = new DuplicateAwareConcordionSpecification(new ConcordionSpecification("2", "titlex", "locationx", false));
    private static final DuplicateAwareConcordionSpecification TEST_3 = new DuplicateAwareConcordionSpecification(new ConcordionSpecification("1, 2", "titley", "locationy", false));

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

    private static TreeSet<ConcordionSpecification> set(ConcordionSpecification... test) {
        return new TreeSet<ConcordionSpecification>(asList(test));
    }
}
