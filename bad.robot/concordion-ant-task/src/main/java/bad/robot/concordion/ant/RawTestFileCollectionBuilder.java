package bad.robot.concordion.ant;

import org.apache.tools.ant.FileScanner;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class RawTestFileCollectionBuilder implements CollectionBuilder<Set<RawTestFile>, RuntimeException>{

    private FileScanner scanner = new DefaultFileScanner();

    public RawTestFileCollectionBuilder with(FileScanner scanner) {
        this.scanner = scanner;
        return this;
    }

    @Override
    public Set<RawTestFile> build() {
        Set<RawTestFile> tests = new HashSet<RawTestFile>();
        for (String filename : scanner.getIncludedFiles()) {
            tests.add(new RawTestFile(scanner.getBasedir(), filename));
        }
        return tests;
    }

    private static class DefaultFileScanner extends FileScannerAdapter {
        @Override
        public File getBasedir() {
            return null;
        }

        public String[] getIncludedFiles() {
            return new String[] {};
        }
    }
}
