package rest;

import java.util.List;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.acl.ACLMessage;

@Path("/messages")
public class MessagesController {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void sendMessage(ACLMessage poruka) {
		// posalji acl poruku
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getPerformatives() {
		// dobavi listu performativa
		return null;
	}
}
