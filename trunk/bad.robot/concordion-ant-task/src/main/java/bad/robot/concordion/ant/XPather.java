package bad.robot.concordion.ant;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;

public class XPather {

    private final XPath xpath = XPathFactory.newInstance().newXPath();
    private final Document document;

    public XPather(File file) throws IOException {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = builder.parse(file);
        } catch (ParserConfigurationException e) {
            throw new IOException(e);
        } catch (SAXException e) {
            throw new IOException(e);
        }
    }

    public String valueOrDefault(String expression, String defaultIfNull) throws IOException {
        try {
            String value = xpath.evaluate(expression, document);
            if ("".equals(value))
                return defaultIfNull;
            return value;
        } catch (XPathExpressionException e) {
            throw new IOException(e);
        }
    }

}
