package bad.robot.concordion.ant;

import java.io.File;
import java.io.IOException;


public class ConcordionSpecificationUnmarshaller implements Unmarshaller<ConcordionSpecification> {

    private static final String UNKNOWN = "unknown";

    public ConcordionSpecification unmarshall(IncludedFile file) throws IOException {
        XPather xpath = new XPather(new File(file.getFullyQualifiedFilename()));
        String group = xpath.valueOrDefault("/html/head/meta[@name='group']/@content", UNKNOWN);
        String iteration = xpath.valueOrDefault("/html/head/meta[@name='iteration']/@content", UNKNOWN);
        String title = xpath.valueOrDefault("/html/head/title", UNKNOWN);
        String ignore = xpath.valueOrDefault("/html/head/meta[@name='ignore']/@content", "false");
        return new ConcordionSpecification(known(group, iteration), title, file.getFilename(), Boolean.valueOf(ignore));
    }

    private static String known(String group, String iteration) {
        if (group.equals(UNKNOWN))
            return iteration;
        return group;
    }

}
