package rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import messages.ACLMessage;

@Path("/messages")
public class MessagesController {

	@POST
	public boolean sendMessage(ACLMessage poruka) {
		return false;
	}

	@GET
	public List<String> getPerformatives() {
		return null;
	}
}
