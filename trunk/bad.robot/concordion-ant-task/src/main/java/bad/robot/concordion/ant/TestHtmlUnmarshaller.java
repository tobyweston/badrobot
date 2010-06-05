package bad.robot.concordion.ant;

import java.io.File;
import java.io.IOException;

public class TestHtmlUnmarshaller implements Unmarshaller<TestHtml> {

    public TestHtml unmarshall(RawTestFile file) throws IOException {
        XPather xpath = new XPather(new File(file.getFullyQualifiedFilename()));
        String iteration = xpath.valueOrDefault("/html/head/meta[@name='iteration']/@content", "unknown");
        String title = xpath.valueOrDefault("/html/head/title", "unknown");
        String ignore = xpath.valueOrDefault("/html/head/meta[@name='ignore']/@content", "false");
        return new TestHtml(iteration, title, file.getFilename(), Boolean.valueOf(ignore));
    }

}
