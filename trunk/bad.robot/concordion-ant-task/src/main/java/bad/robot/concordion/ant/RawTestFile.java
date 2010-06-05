package bad.robot.concordion.ant;

import java.io.File;

public class RawTestFile {
    private final File basedir;
    private final String filename;

    public RawTestFile(File basedir, String filename) {
        this.basedir = basedir;
        this.filename = filename;
    }

    public File getBasedir() {
        return basedir;
    }

    public String getFilename() {
        return filename;
    }

    public String getFullyQualifiedFilename() {
        return getBasedir() + File.separator + getFilename();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RawTestFile other = (RawTestFile) o;

        if (basedir != null) {
            if (!this.getFullyQualifiedFilename().equals(other.getFullyQualifiedFilename())) return false;
        } else {
            if (other.getFullyQualifiedFilename() != null) return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return getFullyQualifiedFilename() != null ? getFullyQualifiedFilename().hashCode() : 0;
    }
}
