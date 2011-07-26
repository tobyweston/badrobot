package bad.robot.turtle.server.rest.map;

import bad.robot.turtle.server.UuidFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import java.util.UUID;

import static bad.robot.turtle.server.UuidFactory.*;
import static bad.robot.turtle.server.rest.map.Get.*;

@Path("/map")
public class MapResource {

    @GET
    @Produces("text/plain")
    @Path("/{id}")
    public String getMapById(@PathParam("id") String id) {
        return get().mapBy(uuid(id));
    }

}
