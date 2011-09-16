package bad.robot.concordion.ant;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;


public class ConcordionSpecificationUnmarshaller implements Unmarshaller<ConcordionSpecification> {

    private static final String UNKNOWN = "unknown";

    public ConcordionSpecification unmarshall(IncludedFile file) throws IOException {
        XPather xpath = new XPather(new File(file.getFullyQualifiedFilename()));
        String group = xpath.valueOrDefault("/html/head/meta[@name='group']/@content", UNKNOWN);
        String iteration = xpath.valueOrDefault("/html/head/meta[@name='iteration']/@content", UNKNOWN);
        String jira = xpath.valueOrDefault("/html/head/meta[@name='jira']/@content", UNKNOWN);
        String title = xpath.valueOrDefault("/html/head/title", UNKNOWN);
        String ignore = xpath.valueOrDefault("/html/head/meta[@name='ignore']/@content", "false");
        return new ConcordionSpecification(combine(group, iteration, jira), title, file.getFilename(), Boolean.valueOf(ignore));
    }

    private static String combine(String group, String iteration, String jira) {
        if (!unknown(group))
            return combine(group, jira);
        if (!unknown(iteration))
            return combine(iteration, jira);
        if (!unknown(jira))
            return prefixJira(jira);
        return UNKNOWN;
    }

    private static boolean unknown(String string) {
        return string.equals(UNKNOWN);
    }

    private static String combine(String value, String jira) {
        if (unknown(jira))
            return value;
        return value + ", " + prefixJira(jira);
    }

    private static String prefixJira(String jiras) {
        StringBuilder builder = new StringBuilder();
        StringTokenizer tokenizer = new StringTokenizer(jiras, ",");
        while (tokenizer.hasMoreElements()) {
            String token = tokenizer.nextToken();
                builder.append(addPrefixTo(token)).append(tokenizer.hasMoreElements() ? ", " : "");
        }
        return builder.toString();
    }

    private static String addPrefixTo(String token) {
        return token.toUpperCase().trim().startsWith("JIRA") ? token : "JIRA " + token.trim();
    }
}


