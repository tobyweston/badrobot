package bad.robot.concordion.ant;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

public class Node {

    static String prettyPrint(org.w3c.dom.Node node) throws TransformerException {
        StringWriter writer = new StringWriter();
        createTransformer().transform(new DOMSource(node), new StreamResult(writer));
        return tidyUp(writer.toString());
    }

    private static Transformer createTransformer() throws TransformerConfigurationException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        return transformer;
    }

    static NodeList tag(String tag, Document document) {
        NodeList list = document.getElementsByTagName(tag);
        if (list == null || list.getLength() == 0)
            throw new IllegalStateException("missing element with id " + tag);
        return list;
    }

    static org.w3c.dom.Node first(NodeList nodes) {
        return nodes.item(0);
    }

    private static String tidyUp(String string) {
        return stripNamespace(stripDivSection(stripNewlinesForWindows(string)));
    }

    private static String stripNewlinesForWindows(String string) {
        return string.replaceAll("\r\n\r\n", "\r\n");
    }

    private static String stripDivSection(String string) {
        return string.replaceFirst("<div id=\"tests\">", "").replaceFirst("</div>", "");
    }

    private static String stripNamespace(String string) {
        return string.replaceFirst(" xmlns:concordion=\"http://www.concordion.org/2007/concordion\"", "");
    }

}
