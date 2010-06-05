package bad.robot.concordion.ant;

public class DuplicateAwareTestHtml extends TestHtml {
    private final Boolean duplicate;

    public DuplicateAwareTestHtml(TestHtml test, boolean duplicate) {
        super(test.getIteration(), test.getTitle(), test.getLocation(), test.isIgnore());
        this.duplicate = duplicate;
    }

    public DuplicateAwareTestHtml(TestHtml testHtml) {
        this(testHtml, false);
    }

    public boolean isDuplicate() {
        return duplicate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DuplicateAwareTestHtml)) return false;
        if (!super.equals(o)) return false;

        DuplicateAwareTestHtml that = (DuplicateAwareTestHtml) o;

        if (duplicate != null ? !duplicate.equals(that.duplicate) : that.duplicate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (duplicate != null ? duplicate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return super.toString() + " duplicate[" + duplicate + "]";
    }
}
