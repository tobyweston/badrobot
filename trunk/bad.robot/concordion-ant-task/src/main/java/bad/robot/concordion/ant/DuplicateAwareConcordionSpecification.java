package bad.robot.concordion.ant;

public class DuplicateAwareConcordionSpecification extends ConcordionSpecification {
    private final Boolean duplicate;

    public DuplicateAwareConcordionSpecification(ConcordionSpecification specification, boolean duplicate) {
        super(specification.getGroup(), specification.getTitle(), specification.getLocation(), specification.isIgnore());
        this.duplicate = duplicate;
    }

    public DuplicateAwareConcordionSpecification(ConcordionSpecification specification) {
        this(specification, false);
    }

    public boolean isDuplicate() {
        return duplicate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DuplicateAwareConcordionSpecification)) return false;
        if (!super.equals(o)) return false;

        DuplicateAwareConcordionSpecification that = (DuplicateAwareConcordionSpecification) o;

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
        return super.toString() + " duplicate[" + duplicate + "]\n";
    }
}
