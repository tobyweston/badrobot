package bad.robot.concordion.ant;

import org.junit.Test;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TestHtmlMapBuilderTest {

    private final TestHtmlMapBuilder builder = new TestHtmlMapBuilder();
    private static final DuplicateAwareTestHtml TEST_1 = new DuplicateAwareTestHtml(new TestHtml("1", "title", "location", false));
    private static final DuplicateAwareTestHtml TEST_2 = new DuplicateAwareTestHtml(new TestHtml("2", "titlex", "locationx", false));
    private static final DuplicateAwareTestHtml TEST_3 = new DuplicateAwareTestHtml(new TestHtml("1, 2", "titley", "locationy", false));

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
        SortedSet<TestHtml> emptySet = new TreeSet<TestHtml>();
        assertThat(builder.with(emptySet).build().isEmpty(), is(true));
    }

    @Test
    public void shouldBuildResults() {
        Map<String, ? extends SortedSet<DuplicateAwareTestHtml>> result = builder.with(set(TEST_1)).build();
        assertThat(result.size(), is(1));
        assertThat(result.get("1"), hasItem(TEST_1));
    }

    @Test
    public void shouldBuildMultipleIterationsResults() {
        TestHtml html = new TestHtml("1, 2", "title", "location", false);
        DuplicateAwareTestHtml test = new DuplicateAwareTestHtml(html);
        Map<String, ? extends SortedSet<DuplicateAwareTestHtml>> result = builder.with(set(test)).build();
        assertThat(result.size(), is(2));
        assertThat(result.get("1"), hasItem(new DuplicateAwareTestHtml(test, false)));
        assertThat(result.get("2"), hasItem(new DuplicateAwareTestHtml(test, true)));
    }

    @Test
    public void shouldBuildMultipleFilesResults() {
        Map<String, ? extends SortedSet<DuplicateAwareTestHtml>> result = builder.with(set(TEST_1, TEST_2)).build();
        assertThat(result.size(), is(2));
        assertThat(result.get("1"), hasItem(TEST_1));
        assertThat(result.get("2"), hasItem(TEST_2));
    }

    @Test
    public void shouldDetectDuplicates() {
        Map<String, ? extends SortedSet<DuplicateAwareTestHtml>> result = builder.with(set(TEST_3)).build();
        assertThat(result.size(), is(2));
        assertThat(result.get("1").first().isDuplicate(), is(false));
        assertThat(result.get("2").first().isDuplicate(), is(true));
    }

    private static TreeSet<TestHtml> set(TestHtml... test) {
        return new TreeSet<TestHtml>(asList(test));
    }
}
