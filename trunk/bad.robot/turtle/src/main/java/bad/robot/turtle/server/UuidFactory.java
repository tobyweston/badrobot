package bad.robot.turtle.server;

import java.util.UUID;

public class UuidFactory {

    public static UUID uuid(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new InvalidUuidException(e.getMessage());
        }
    }

}
