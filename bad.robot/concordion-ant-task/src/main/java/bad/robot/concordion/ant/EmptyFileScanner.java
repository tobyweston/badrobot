package bad.robot.concordion.ant;

import java.io.File;

class EmptyFileScanner extends FileScannerAdapter {

    @Override
    public File getBasedir() {
        return null;
    }

    public String[] getIncludedFiles() {
        return new String[] {};
    }
}
