package bad.robot.concordion.ant.manual.groupingtests;

import bad.robot.concordion.ant.XPather;

import java.io.*;

import static bad.robot.concordion.ant.FileUtils.read;

class PageReader {
    
    private final String page;

    public PageReader(String page) {
        this.page = page;
    }

    public String asString() throws IOException {
        return read(fileReader());
    }

    private BufferedReader fileReader() throws FileNotFoundException {
        return new BufferedReader(new FileReader(page));
    }

    public String groupSectionAsString() throws IOException {
        String expression = "/html/body/div[@id='tests']";
        String section = new XPather(new File(page)).valueOrDefault("{/html/text()}", "No content or missing element");
        String s = htmlFriendly(section);
        System.out.println("s = " + s);
        return s;
    }

    private static String htmlFriendly(String section) {
        return section.replace("<", "&lt;").replace(">", "&rt");
    }
}
