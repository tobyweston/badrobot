package bad.robot.concordion.ant;

import org.apache.tools.ant.FileScanner;

import java.util.HashSet;
import java.util.Set;

public class IncludedFiles implements CollectionBuilder<Set<IncludedFile>, RuntimeException>{

    private FileScanner scanner = new EmptyFileScanner();

    public IncludedFiles with(FileScanner scanner) {
        this.scanner = scanner;
        return this;
    }

    public Set<IncludedFile> build() {
        Set<IncludedFile> files = new HashSet<IncludedFile>();
        for (String filename : scanner.getIncludedFiles()) {
            files.add(new IncludedFile(scanner.getBasedir(), filename));
        }
        return files;
    }

}
