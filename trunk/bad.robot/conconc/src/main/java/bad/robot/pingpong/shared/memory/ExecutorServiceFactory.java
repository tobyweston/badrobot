package bad.robot.pingpong.shared.memory;

import java.util.concurrent.ExecutorService;

public interface ExecutorServiceFactory {
    ExecutorService create();
}
