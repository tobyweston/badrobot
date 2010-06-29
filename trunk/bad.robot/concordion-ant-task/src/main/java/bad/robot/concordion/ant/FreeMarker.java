package bad.robot.concordion.ant;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class FreeMarker {

    private final Configuration configuration = new Configuration();

    public FreeMarker(File folder) throws IOException {
        if (!folder.isDirectory())
            throw new IllegalArgumentException();
        configuration.setDirectoryForTemplateLoading(folder);
        configuration.setObjectWrapper(new DefaultObjectWrapper());
    }

    public void merge(File template, Map<String, Object> model, Writer writer) throws IOException, TemplateException {
        configuration.getTemplate(template.getName()).process(model, writer);
        writer.flush();
    }
}
