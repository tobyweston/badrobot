package bad.robot.concordion.ant;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.apache.tools.ant.Project.MSG_VERBOSE;

public class GenerateTestOverviewTask extends Task {

    private FileSet tests;
    private File output;
    private File template;

    public void setOutput(File output) {
        this.output = output;
    }

    public void setTemplate(File template) {
        this.template = template;
    }

    public void add(FileSet tests) {
        this.tests = tests;
    }

    @Override
    public void execute() throws BuildException {
        assertNotNull(output, template, tests);
        mergeTemplate(new RawTestFileCollectionBuilder().with(tests.getDirectoryScanner()).build());
    }

    private void mergeTemplate(Set<RawTestFile> tests) {
        log(tests);
        Configuration configuration = new Configuration();
        try {
            configuration.setDirectoryForTemplateLoading(template.getParentFile());
            configuration.setObjectWrapper(new DefaultObjectWrapper());

            TestHtmlCollectionBuilder setBuilder = new TestHtmlCollectionBuilder().with(new TestHtmlUnmarshaller()).with(tests);
            TestHtmlMapBuilder mapBuilder = new TestHtmlMapBuilder().with(setBuilder.build());
            
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("tests", mapBuilder.build());
            model.put("date", new Date());

            FileWriter writer = new FileWriter(output);
            configuration.getTemplate(template.getName()).process(model, writer);
            writer.flush();
        } catch (Exception e) {
            throw new BuildException(e);
        }
    }

    private void log(Set<RawTestFile> tests) {
        log("looking for files in " + this.tests.getDir().toString() + "...", MSG_VERBOSE);
        for (RawTestFile test : tests)
            log("   " + test.getFilename(), MSG_VERBOSE);
    }

    private static void assertNotNull(Object... objects) {
        for (Object object : objects)
            if (object == null)
                throw new BuildException("The attributes output and template as well as a nested resource collection must be set");
    }

}