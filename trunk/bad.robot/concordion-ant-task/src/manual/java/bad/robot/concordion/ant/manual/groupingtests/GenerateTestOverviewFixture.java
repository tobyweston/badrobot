package bad.robot.concordion.ant.manual.groupingtests;

import bad.robot.concordion.ant.GenerateTestOverviewTask;

import javax.xml.transform.TransformerException;
import java.io.IOException;

import static bad.robot.concordion.ant.manual.groupingtests.TaskFactory.createTask;

public class GenerateTestOverviewFixture {
             
    private GenerateTestOverviewTask task;

    public void generateOverviewFullPage(String... pages) throws IOException {
        task = createTask("Overview.ftl", pages);
        task.execute();
    }

    public String getOverviewGroupSection() throws IOException, TransformerException {
        return new PageReader(task.getOutput()).groupSectionAsString();
    }

    public String getOverviewGroups() throws IOException, TransformerException {
        return new PageReader(task.getOutput()).groupSectionAsList();
    }

}
