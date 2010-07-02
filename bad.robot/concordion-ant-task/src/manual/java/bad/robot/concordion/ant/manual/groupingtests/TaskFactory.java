package bad.robot.concordion.ant.manual.groupingtests;

import bad.robot.concordion.ant.GenerateTestOverviewTask;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;

import static bad.robot.concordion.ant.FileUtils.*;
import static java.lang.String.format;

public class TaskFactory {

    public static GenerateTestOverviewTask createTask(String page, String template) throws IOException {
        GenerateTestOverviewTask task = new GenerateTestOverviewTask();
        File output = createTemporaryFile(workingFolder());
        task.setOutput(output);
        task.setTemplate(first(findRecursively(workingFolder(), matching(template))));
        task.add(createFileSet(page, first(findRecursively(workingFolder(), matching(page)))));
        return task;
    }

    private static FileSet createFileSet(String page, File file) {
        FileSet fileSet = new FileSet();
        fileSet.setDir(file.getParentFile());
        fileSet.setIncludes(page);
        fileSet.setProject(new Project());
        return fileSet;
    }

    private static FilenameFilter matching(final String page) {
        return new FilenameFilter() {
            public boolean accept(File folder, String target) {
                return page.equals(target);
            }
        };
    }

    private static File first(List<File> files) {
        if (files.isEmpty())
            throw new IllegalStateException(format("couldn't find file looking recursively from %s", workingFolder().getAbsolutePath()));
        return files.get(0);
    }
    
}
