package bad.robot.concordion.ant;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static bad.robot.concordion.ant.Node.*;

public class PageParser {

    private final Document document;

    public PageParser(File file) throws IOException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(file);
        } catch (ParserConfigurationException e) {
            throw new IOException(e);
        } catch (SAXException e) {
            throw new IOException(e);
        }
    }

    public String getTestSection() throws TransformerException {
        return prettyPrint(first(tag("div", document)));
    }

    public String getTestSectionSummarised() {
        return asString(tag("h2", document));
    }

    private static String asString(NodeList groups) {
        return Arrays.toString(toList(groups).toArray()).replace(", ","\n").replace("[","").replace("]", "");
    }

    private static List<String> toList(NodeList nodes) {
        List<String> list = new ArrayList<String>();
        for (int index = 0; index < nodes.getLength(); index++)
            list.add(nodes.item(index).getTextContent());
        return list;
    }
}
