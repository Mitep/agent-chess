package rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/node")
public class NodeController {

	@DELETE
	@Path("/{alias}")
	public boolean delete() {
		return false;
	}

	@GET
	public String getNode() {
		return "";
	}
}
