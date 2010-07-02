package bad.robot.concordion.ant.manual.groupingtests;

import bad.robot.concordion.ant.GenerateTestOverviewTask;

import java.io.IOException;

import static bad.robot.concordion.ant.manual.groupingtests.TaskFactory.createTask;

public class GenerateTestOverviewFixture {

    private GenerateTestOverviewTask task;

    public void generateOverviewFullPage(final String page) throws IOException {
        task = createTask(page, "Overview.ftl");
        task.execute();
    }

    public String getOverviewGroupSection() throws IOException {
        return new PageReader(task.getOutput()).groupSectionAsString();
    }

}
