package bad.robot.turtle.server.rest.map;

import bad.robot.turtle.server.rest.GetByUuid;

import java.util.UUID;

class Get implements GetByUuid {

    static Get get() {
        return new Get();
    }

    private Get() {
    }

    @Override
    public String mapBy(UUID uuid) {
        return "hello " + uuid;
    }
}
