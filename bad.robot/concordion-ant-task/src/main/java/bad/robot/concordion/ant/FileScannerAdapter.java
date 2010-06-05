package bad.robot.concordion.ant;

import org.apache.tools.ant.FileScanner;

import java.io.File;

class FileScannerAdapter implements FileScanner {
    @Override
    public void addDefaultExcludes() {
    }

    @Override
    public File getBasedir() {
        return null;
    }

    @Override
    public String[] getExcludedDirectories() {
        return new String[0];
    }

    @Override
    public String[] getExcludedFiles() {
        return new String[0];
    }

    @Override
    public String[] getIncludedDirectories() {
        return new String[0];
    }

    @Override
    public String[] getIncludedFiles() {
        return new String[0];
    }

    @Override
    public String[] getNotIncludedDirectories() {
        return new String[0];
    }

    @Override
    public String[] getNotIncludedFiles() {
        return new String[0];
    }

    @Override
    public void scan() throws IllegalStateException {

    }

    @Override
    public void setBasedir(String s) {

    }

    @Override
    public void setBasedir(File file) {

    }

    @Override
    public void setExcludes(String[] strings) {

    }

    @Override
    public void setIncludes(String[] strings) {

    }

    @Override
    public void setCaseSensitive(boolean b) {

    }
}
