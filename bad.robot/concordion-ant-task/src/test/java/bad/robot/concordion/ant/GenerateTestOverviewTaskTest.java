package bad.robot.concordion.ant;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static bad.robot.concordion.ant.Assertions.expectingExceptionWithMessage;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GenerateTestOverviewTaskTest {

    private final GenerateTestOverviewTask task = new GenerateTestOverviewTask();

    private static final String ERROR_MESSAGE = "The attributes output and template as well as a nested resource collection must be set";

    private static final boolean DUPLICATE = true;
    private static final boolean DONT_IGNORE = false;
    private static final boolean IGNORE = true;

    private static final List<DuplicateAwareTestHtml> EXPECTED_HTMLS = new ArrayList<DuplicateAwareTestHtml>() {{
        add(new DuplicateAwareTestHtml(new TestHtml("69", "Test1", combineWithFileSeperator("src", "test", "resources", "specs", "Test1.html"), DONT_IGNORE)));
        add(new DuplicateAwareTestHtml(new TestHtml("69", "Test2", combineWithFileSeperator("src", "test", "resources", "specs", "Test2.html"), DONT_IGNORE)));
        add(new DuplicateAwareTestHtml(new TestHtml("68, 69", "Test3", combineWithFileSeperator("src", "test", "resources", "specs", "overviewpage", "Test3.html"), DONT_IGNORE), DUPLICATE));
        add(new DuplicateAwareTestHtml(new TestHtml("69", "Test4", combineWithFileSeperator("src", "test", "resources", "specs", "overviewpage", "Test4.html"), DONT_IGNORE)));
        add(new DuplicateAwareTestHtml(new TestHtml("68, 69", "Test3", combineWithFileSeperator("src", "test", "resources", "specs", "overviewpage", "Test3.html"), DONT_IGNORE)));
        add(new DuplicateAwareTestHtml(new TestHtml("unknown", "OverViewPage", combineWithFileSeperator("src", "test", "resources", "specs", "overviewpage", "OverViewPage.html"), IGNORE)));
        add(new DuplicateAwareTestHtml(new TestHtml("unknown", "unknown", combineWithFileSeperator("src", "test", "resources", "specs", "Specs.html"), DONT_IGNORE)));
    }};
    private static final String WORKING_FOLDER = ".";

    private static String combineWithFileSeperator(String... resources) {
        StringBuilder builder = new StringBuilder();
        for (String resource : resources)
            builder.append(resource).append(File.separator);
        String combination = builder.toString();
        return combination.substring(0, combination.lastIndexOf(File.separator));
    }

    @Test
    public void shouldNotAllowNullParametersNullFile() {
        setParametersOnTask(task, null, dummyFile(), dummyFileSet());
        expectingExceptionWithMessage(executeTask(), ERROR_MESSAGE);
    }

    @Test
    public void shouldNotAllowNullParametersNullTemplate() {
        setParametersOnTask(task, dummyFile(), null, dummyFileSet());
        expectingExceptionWithMessage(executeTask(), ERROR_MESSAGE);
    }

    @Test
    public void shouldNotAllowNullParametersNullFileSet() {
        setParametersOnTask(task, dummyFile(), dummyFile(), null);
        expectingExceptionWithMessage(executeTask(), ERROR_MESSAGE);
    }

    @Test
    public void shouldGeneratePhysicalOverviewPage() throws IOException, SAXException, XPathExpressionException, ParserConfigurationException {
        File target = File.createTempFile("Overview", ".html", new File(WORKING_FOLDER));
        target.deleteOnExit();
        GenerateTestOverviewTask task = createTask(WORKING_FOLDER, "**/src/**/specs/**/*.html", target.getAbsolutePath());
        task.execute();
        assertThat(target.exists(), is(true));
        List<DuplicateAwareTestHtml> results = new OverviewHtmlUnmarshaller().getFrom(target);
        assertThat(results, TestHtmlMatcher.containsAll(EXPECTED_HTMLS));
    }

    private static GenerateTestOverviewTask createTask(String searchFolder, String includes, String output) {
        FileSet fileSet = new FileSet();
        fileSet.setDir(verify(new File(searchFolder)));
        fileSet.setProject(new Project());
        fileSet.setIncludes(includes);
        GenerateTestOverviewTask task = new GenerateTestOverviewTask();
        task.setOutput(new File(output));
        task.setTemplate(verify(find("example.ftl")));
        task.add(fileSet);
        return task;
    }

    private static File find(String file) {
        return new File(GenerateTestOverviewTaskTest.class.getResource(file).getFile());
    }

    private static File verify(File file) {
        assertThat("file " + file.getAbsolutePath() + " doesn't exist", file.exists(), is(true));
        return file;
    }

    private void setParametersOnTask(GenerateTestOverviewTask task, File output, File template, FileSet fileSet) {
        task.setOutput(output);
        task.setTemplate(template);
        task.add(fileSet);
    }

    private static FileSet dummyFileSet() {
        FileSet fileSet = new FileSet();
        fileSet.setDir(dummyFile());
        return fileSet;
    }

    private static File dummyFile() {
        return new File("");
    }

    private Callable<Void> executeTask() {
        return new Callable<Void>() {
            @Override
            public Void call() throws BuildException {
                task.execute();
                return null;
            }
        };
    }

}
