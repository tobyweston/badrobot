package bad.robot.concordion.ant.manual.groupingtests;

import bad.robot.concordion.ant.XmlParser;

import javax.xml.transform.TransformerException;
import java.io.*;

import static bad.robot.concordion.ant.FileUtils.read;

class PageReader {
    
    private final String page;

    public PageReader(String page) {
        this.page = page;
        try {
            System.out.println(asString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String asString() throws IOException {
        return read(fileReader());
    }

    private BufferedReader fileReader() throws FileNotFoundException {
        return new BufferedReader(new FileReader(page));
    }

    public String groupSectionAsString() throws IOException, TransformerException {
        return new XmlParser(new File(page)).getTestSection();
    }

}
