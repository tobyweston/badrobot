package bad.robot.concordion.ant.manual.groupingtests;

import org.concordion.api.ExpectedToPass;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

import javax.xml.transform.TransformerException;
import java.io.IOException;

@RunWith(ConcordionRunner.class)
@ExpectedToPass
public class AddingMultipleGroupsAreSorted {

    private final GenerateTestOverviewFixture fixture = new GenerateTestOverviewFixture();

    public void generateOverview(String page) throws IOException {
        fixture.generateOverviewFullPage(page);
    }

    public String getOverviewGroups() throws IOException, TransformerException {
        return fixture.getOverviewGroups();
    }

}
