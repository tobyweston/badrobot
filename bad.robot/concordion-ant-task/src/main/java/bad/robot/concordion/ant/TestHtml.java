package bad.robot.concordion.ant;

public class TestHtml implements Comparable<TestHtml> {

    private final String group;
    private final String title;
    private final String location;
    private final boolean ignore;

    public TestHtml(String group, String title, String location, boolean ignore) {
        this.group = group;
        this.title = title;
        this.location = location;
        this.ignore = ignore;
    }

    public String getGroup() {
        return group;
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
        if (group != null ? !group.equals(testHtml.group) : testHtml.group != null) return false;
        if (location != null ? !location.equals(testHtml.location) : testHtml.location != null) return false;
        if (title != null ? !title.equals(testHtml.title) : testHtml.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = group != null ? group.hashCode() : 0;
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
        return "title[" + title + "] group[" + group + "] location[" + location + "]" + " ignore[" + ignore + "]";
    }
}
