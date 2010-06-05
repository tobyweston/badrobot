package bad.robot.concordion.ant;

import java.io.IOException;

public interface Unmarshaller<T> {

    T unmarshall(RawTestFile file) throws IOException;
}
