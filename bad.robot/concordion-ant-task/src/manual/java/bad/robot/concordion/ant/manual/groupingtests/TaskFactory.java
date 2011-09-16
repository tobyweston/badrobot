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

    private static File getParentFolder(String... pages) {
        Set<File> folders = new HashSet<File>();
        for (String page : pages)
            folders.add(first(findRecursively(workingFolder(), matching(page))).getParentFile());
        if (folders.size() != 1)
            throw new RuntimeException("incorrect number of folders found, you can only test Concordion specfications from the same folder");
        return first(folders);
    }

    private static FilenameFilter matching(final String page) {
        return new FilenameFilter() {
            public boolean accept(File folder, String target) {
                return page.equals(target);// && folder.getAbsolutePath().contains("resources");
            }
        };
    }

    private static <T> T first(List<T> objects) {
        if (objects.isEmpty())
            throw new IllegalStateException(format("couldn't find file looking recursively from %s", workingFolder().getAbsolutePath()));
        return objects.get(0);
    }

    private static <T> T first(Set<T> objects) {
        return (T) objects.toArray()[0];
    }

}
