package bad.robot.concordion.ant.manual.groupingtests;

import org.concordion.api.ExpectedToFail;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

import javax.xml.transform.TransformerException;
import java.io.IOException;

@RunWith(ConcordionRunner.class)
@ExpectedToFail
public class AddingDuplicateTests {

    private final GenerateTestOverviewFixture fixture = new GenerateTestOverviewFixture();

    public void generateOverview(String duplicate) throws IOException {
        fixture.generateOverviewFullPage(duplicate, duplicate);
    }

    public String getOverview() throws IOException, TransformerException {
        return fixture.getOverviewGroupSection();
    }

}
