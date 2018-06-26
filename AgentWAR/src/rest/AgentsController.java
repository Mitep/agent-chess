package rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/agents")
public class AgentsController {

	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return "agent test works!";
	}

	@GET
	@Path("/classes")
	@Produces(MediaType.TEXT_PLAIN)
	public List<String> getClasses() {
		return null;
	}

	@POST
	@Path("/classes")
	@Produces(MediaType.TEXT_PLAIN)
	public void classes() {
	}

	@POST
	@Path("/running")
	@Produces(MediaType.TEXT_PLAIN)
	public void running() {
	}

	@PUT
	@Path("/running/{type}/{name}")
	public void startAgent() {
	}
}
