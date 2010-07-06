package bad.robot.concordion.ant.manual.groupingtests;

import org.concordion.api.Unimplemented;
import org.concordion.integration.junit4.ConcordionRunner;
import org.junit.runner.RunWith;

import javax.xml.transform.TransformerException;
import java.io.IOException;

@RunWith(ConcordionRunner.class)
@Unimplemented
public class AddingGroupMeta {

    private final GenerateTestOverviewFixture fixture = new GenerateTestOverviewFixture();

    public void generateOverview(String page) throws IOException {
        fixture.generateOverviewFullPage(page);
    }

    public String getOverview() throws IOException, TransformerException {
        String s = fixture.getOverviewGroupSection();
        System.out.println("s = " + s); 
        return s;
    }

}
