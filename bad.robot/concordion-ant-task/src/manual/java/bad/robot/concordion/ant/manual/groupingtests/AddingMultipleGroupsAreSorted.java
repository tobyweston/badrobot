package bad.robot.concordion.ant.manual.groupingtests;

import org.concordion.api.ExpectedToFail;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

import javax.xml.transform.TransformerException;
import java.io.IOException;

@RunWith(ConcordionRunner.class)
@ExpectedToFail
public class AddingMultipleGroupsAreSorted {

    private final GenerateTestOverviewFixture fixture = new GenerateTestOverviewFixture();

    public void generateOverview(String page) throws IOException {
        fixture.generateOverviewFullPage(page);
    }

    public String getOverviewGroups() throws IOException, TransformerException {
        return fixture.getOverviewGroups();
    }

}