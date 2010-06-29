package bad.robot.concordion.ant;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ConcordionSpecificationUnmarshallerTest {

    private final ConcordionSpecificationUnmarshaller unmarshaller = new ConcordionSpecificationUnmarshaller();


    @Test(expected = IOException.class)
    public void shouldFailForMissingFiles() throws Exception {
        unmarshaller.unmarshall(testFile("no-file.html"));
    }

    @Test
    public void shouldNotFailWhenFileExists() throws Exception {
        unmarshaller.unmarshall(file("sample.html"));
    }

    @Test
    public void shouldSetGroup() throws IOException {
        assertThat(unmarshaller.unmarshall(file("sample.html")).getGroup(), is("0, 1, 2"));
    }

    @Test
    public void shouldSetTitle() throws IOException {
        assertThat(unmarshaller.unmarshall(file("sample.html")).getTitle(), is("Sample Web Page"));
    }

    @Test
    public void shouldSetLocation() throws IOException {
        assertThat(unmarshaller.unmarshall(file("sample.html")).getLocation(), is("sample.html"));
    }

    @Test
    public void shouldSetLocationAsRelativeUrl() throws IOException {
        assertThat(unmarshaller.unmarshall(file("sample.html")).getLocationAsRelativeUrl(), is("sample.html"));
    }

    @Test
    public void shouldAllowIterationAsASubstituteForGroup() throws IOException {
        assertThat(unmarshaller.unmarshall(file("noGroup.html")).getGroup(), is("8"));
    }

    private static IncludedFile file(String name) {
        String fullyQualifiedFilename = SetOfConcordionSpecificationsTest.class.getResource(name).getFile();
        String baseDir = fullyQualifiedFilename.replace(name, "");
        return new IncludedFile(new File(baseDir), name);
    }

    private static IncludedFile testFile(String filename) {
        return new IncludedFile(dummyFile(), filename);
    }

    private static File dummyFile() {
        return new File(".");
    }


}
