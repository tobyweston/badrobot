package bad.robot.pingpong.shared.memory.optimistic;

import bad.robot.pingpong.shared.memory.LongCounter;
import bad.robot.pingpong.shared.memory.RealClock;
import bad.robot.pingpong.shared.memory.ThreadLocalStopWatch;
import bad.robot.pingpong.shared.memory.Throughput;
import bad.robot.pingpong.shared.memory.pessimistic.AtomicMillisecondCounter;

public class OptimisticThroughput {

    public static Throughput createThreadUnsafeThroughput() {
        return new Throughput(new ThreadLocalStopWatch(new RealClock()), new LongCounter(), new AtomicMillisecondCounter());
    }

    public static Throughput createThreadSafeThroughput() {
        return new Throughput(new ThreadLocalStopWatch(new RealClock()), new LongCounter(), new AtomicMillisecondCounter());
    }
}
