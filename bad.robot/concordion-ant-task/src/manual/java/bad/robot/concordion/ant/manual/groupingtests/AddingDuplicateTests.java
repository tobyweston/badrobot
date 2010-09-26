package bad.robot.concordion.ant.manual.groupingtests;

import org.concordion.api.ExpectedToPass;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

import javax.xml.transform.TransformerException;
import java.io.IOException;

@RunWith(ConcordionRunner.class)
@ExpectedToPass
public class AddingDuplicateTests {

    private final GenerateTestOverviewFixture fixture = new GenerateTestOverviewFixture();

    public void generateOverview(String test) throws IOException {
        fixture.generateOverviewFullPage(test, "AddingGroupMeta.html");
    }

    public String getOverview() throws IOException, TransformerException {
        return fixture.getOverviewGroupSection();
    }

}
