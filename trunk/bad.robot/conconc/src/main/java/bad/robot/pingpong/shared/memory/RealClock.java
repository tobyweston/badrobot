package bad.robot.pingpong.shared.memory;

import com.google.code.tempusfugit.FactoryException;
import com.google.code.tempusfugit.temporal.Clock;

import java.util.Date;

public class RealClock implements Clock {
    @Override
    public Date create() throws FactoryException {
        return new Date();
    }
}
