package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/node")
public class NodeController {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void connectNodes() {
		// ovde imamo vise razlicihit requestova
		// treba napraviti neki dto sa tipom poruke i contetom da znamo sta radimo
	} 
	
	@DELETE
	@Path("/{alias}")
	public boolean delete() {
		return false;
	}

	
}
