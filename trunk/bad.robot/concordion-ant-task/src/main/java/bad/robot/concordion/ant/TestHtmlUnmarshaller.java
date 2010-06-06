package bad.robot.concordion.ant;

import java.io.File;
import java.io.IOException;

public class TestHtmlUnmarshaller implements Unmarshaller<TestHtml> {

    public TestHtml unmarshall(RawTestFile file) throws IOException {
        XPather xpath = new XPather(new File(file.getFullyQualifiedFilename()));
        String group = xpath.valueOrDefault("/html/head/meta[@name='group']/@content", "unknown");
        String title = xpath.valueOrDefault("/html/head/title", "unknown");
        String ignore = xpath.valueOrDefault("/html/head/meta[@name='ignore']/@content", "false");
        return new TestHtml(group, title, file.getFilename(), Boolean.valueOf(ignore));
    }

}
