package bad.robot.concordion.ant;

import java.util.concurrent.Callable;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

class Assertions {

    public static void expectingExceptionWithMessage(Callable<Void> callable, String message) {
        try {
            callable.call();
            fail();
        } catch (Exception e) {
            assertThat(e.getMessage(), is(message));
        }
    }
}
