import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path("/weather")
@Produces({"application/json", "application/xml"})
@Produces({"application/json", "application/xml"})
public class WeatherService {
	@GET
    @Path("/")
    @Produces({MediaType.APPLICATION_JSON, MediaType.})
}