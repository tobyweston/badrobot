package bad.robot.concordion.ant;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        mergeTemplate(new IncludedFiles().with(tests.getDirectoryScanner()).build());
    }

    private void mergeTemplate(Set<IncludedFile> tests) {
        log(tests);
        try {
            SetOfConcordionSpecifications set = new SetOfConcordionSpecifications().with(new ConcordionSpecificationUnmarshaller()).with(tests);
            GroupedCollectionOfConcordionSpecifications group = new GroupedCollectionOfConcordionSpecifications().with(set.build());
            new FreeMarker(template.getParentFile()).merge(template, createModel(group), new FileWriter(output));
        } catch (Exception e) {
            throw new BuildException(e);
        }
    }

    private static Map<String, Object> createModel(GroupedCollectionOfConcordionSpecifications tests) throws IOException {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("tests", tests.build());
        model.put("date", new Date());
        return model;
    }

    private void log(Set<IncludedFile> tests) {
        log("looking for files in " + this.tests.getDir().toString() + "...", MSG_VERBOSE);
        for (IncludedFile test : tests)
            log("   " + test.getFilename(), MSG_VERBOSE);
    }

    private static void assertNotNull(Object... objects) {
        for (Object object : objects)
            if (object == null)
                throw new BuildException("The attributes output and template as well as a nested resource collection must be set");
    }

    public String getOutput() {
        return output.getAbsolutePath();
    }

}