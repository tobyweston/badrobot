package bad.robot.concordion.ant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class FileUtils {

    public static List<File> findRecursively(File root, FilenameFilter filter) {
        List<File> files = new ArrayList<File>();
        files.addAll(nullSafeList(root, filter));
        for (File file : nullSafeList(root, new FolderFilter()))
            files.addAll(findRecursively(file, filter));
        return files;
    }

    private static List<File> nullSafeList(File root, FilenameFilter filter) {
        File[] matches = root.listFiles(filter);
        if (matches == null)
            return emptyList();
        return asList(matches);
    }

    public static String read(BufferedReader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null)
            builder.append(line);
        return builder.toString();
    }

    public static class FolderFilter implements FilenameFilter {
        public boolean accept(File folder, String filename) {
            return folder.isDirectory() && !filename.endsWith(".svn");
        }
    }

    public static File createTemporaryFile(File folder) throws IOException {
        File file = File.createTempFile("Overview", ".html", folder);
        file.deleteOnExit();
        return file;
    }

    public static File workingFolder() {
        return new File(".");
    }
                                                                   
}
