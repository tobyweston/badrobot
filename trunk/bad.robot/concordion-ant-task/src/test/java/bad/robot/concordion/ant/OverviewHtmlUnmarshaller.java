package bad.robot.concordion.ant;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

class OverviewHtmlUnmarshaller {

    public List<DuplicateAwareTestHtml> getFrom(File file) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        Document document = parseDocument(new FileReader(file));
        NodeList nodes = (NodeList) xpath().evaluate("//a", document, XPathConstants.NODESET);
        List<DuplicateAwareTestHtml> htmls = new ArrayList<DuplicateAwareTestHtml>();
        for (int i = 0; i < nodes.getLength(); i++) {
            String iteration = nodes.item(i).getAttributes().getNamedItem("iteration").getNodeValue();
            String location = nodes.item(i).getAttributes().getNamedItem("location").getNodeValue();
            String title = nodes.item(i).getAttributes().getNamedItem("title").getNodeValue();
            String ignore = nodes.item(i).getAttributes().getNamedItem("ignore").getNodeValue();
            String duplicate = nodes.item(i).getAttributes().getNamedItem("duplicate").getNodeValue();
            htmls.add(new DuplicateAwareTestHtml(new TestHtml(iteration, title, location, Boolean.valueOf(ignore)), Boolean.valueOf(duplicate)));
        }
        return htmls;
    }

    private Document parseDocument(Reader reader) throws SAXException, IOException, ParserConfigurationException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(reader));
    }

    private XPath xpath() {
        return XPathFactory.newInstance().newXPath();
    }
}
