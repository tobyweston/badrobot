package bad.robot.concordion.ant;

import org.apache.tools.ant.FileScanner;

import java.io.File;

class FileScannerAdapter implements FileScanner {

    public void addDefaultExcludes() {
    }

    public File getBasedir() {
        return null;
    }

    public String[] getExcludedDirectories() {
        return new String[0];
    }

    public String[] getExcludedFiles() {
        return new String[0];
    }

    public String[] getIncludedDirectories() {
        return new String[0];
    }

    public String[] getIncludedFiles() {
        return new String[0];
    }

    public String[] getNotIncludedDirectories() {
        return new String[0];
    }

    public String[] getNotIncludedFiles() {
        return new String[0];
    }

    public void scan() throws IllegalStateException {
    }

    public void setBasedir(String s) {
    }

    public void setBasedir(File file) {
    }

    public void setExcludes(String[] strings) {
    }

    public void setIncludes(String[] strings) {
    }

    public void setCaseSensitive(boolean b) {
    }
}
