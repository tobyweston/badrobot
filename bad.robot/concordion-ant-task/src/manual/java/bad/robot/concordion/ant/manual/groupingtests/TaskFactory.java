package bad.robot.concordion.ant.manual.groupingtests;

import bad.robot.concordion.ant.GenerateTestOverviewTask;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static bad.robot.concordion.ant.FileUtils.*;
import static java.lang.String.format;

public class TaskFactory {

    public static GenerateTestOverviewTask createTask(String template, String... pages) throws IOException {
        GenerateTestOverviewTask task = new GenerateTestOverviewTask();
        File output = createTemporaryFile(workingFolder());
        task.setOutput(output);
        task.setTemplate(first(findRecursively(workingFolder(), matching(template))));
        task.add(createFileSet(pages));
        return task;
    }

    private static FileSet createFileSet(String... pages) {
        FileSet fileSet = new FileSet();
        File folder = getParentFolder(pages);
        fileSet.setDir(folder);
        fileSet.setIncludes(concatenationOf(pages));
        fileSet.setProject(new Project());
        return fileSet;
    }

    private static String concatenationOf(String... pages) {
        StringBuilder builder = new StringBuilder();
        for (int current = 0; current < pages.length; current++)
            builder.append(pages[current]).append(",");
        return builder.toString();
    }

    private static File getParentFolder(String[] pages) {
        Set<File> files = new HashSet<File>();
        for (String page : pages)
            files.add(first(findRecursively(workingFolder(), matching(page))));
        if (files.size() != 1)
            throw new RuntimeException("incorrect number of folders found, you can only test Concordion specfications from the same folder");
        return files.toArray(new File[]{})[0].getParentFile();

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
