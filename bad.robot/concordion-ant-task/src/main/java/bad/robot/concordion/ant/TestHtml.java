package bad.robot.concordion.ant;

public class TestHtml implements Comparable<TestHtml> {

    private final String iteration;
    private final String title;
    private final String location;
    private final boolean ignore;

    public TestHtml(String iteration, String title, String location, boolean ignore) {
        this.iteration = iteration;
        this.title = title;
        this.location = location;
        this.ignore = ignore;
    }

    public String getIteration() {
        return iteration;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public String getLocationAsRelativeUrl() {
        return location.replace("\\", "/");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestHtml testHtml = (TestHtml) o;

        if (ignore != testHtml.ignore) return false;
        if (iteration != null ? !iteration.equals(testHtml.iteration) : testHtml.iteration != null) return false;
        if (location != null ? !location.equals(testHtml.location) : testHtml.location != null) return false;
        if (title != null ? !title.equals(testHtml.title) : testHtml.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = iteration != null ? iteration.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (ignore ? 1 : 0);
        return result;
    }

    @Override
    public int compareTo(TestHtml other) {
        return this.title.compareTo(other.title);
    }

    @Override
    public String toString() {
        return "title[" + title + "] iteration[" + iteration + "] location[" + location + "]" + " ignore[" + ignore + "]";
    }
}
