package bad.robot.pingpong.shared.memory;

import com.google.code.tempusfugit.FactoryException;

import java.util.Date;

public class RealClock implements Clock {
    @Override
    public Date time() throws FactoryException {
        return new Date();
    }
}
