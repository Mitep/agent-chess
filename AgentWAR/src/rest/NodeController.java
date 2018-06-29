package rest;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import services.interfaces.RestNodeLocal;
import utils.JsonUtils;

@Path("/node")
public class NodeController {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void connectNodes(String request) {
		try {
			Context context = new InitialContext();
			RestNodeLocal rnl = (RestNodeLocal) context.lookup(RestNodeLocal.LOOKUP);

			 switch (JsonUtils.getNodeRequestType(request)) {
	         	case "contact_master": rnl.connectSlave(JsonUtils.getNodeRequestSlaveAddres(request)); // slave kontaktira mastera
	         		break;
	         	case "new_slave_to_slaves": // rnl.newSlaveNode(slave); // master salje svim cvorovima novi cvor
	         		break;
	         	case "slaves_to_new_slave": // rnl.newSlavesNode(slaves); // master salje cvorove novom cvoru
	         		break;
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
	// master salje ostalim cvorovima da obrisu slave
	@DELETE
	@Path("/{alias}")
	public void deleteNode(@PathParam("alias") String alias) {
		try {
			Context context = new InitialContext();
			RestNodeLocal rnl = (RestNodeLocal) context.lookup(RestNodeLocal.LOOKUP);
			rnl.deleteNode(alias);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// heartbeat protokol
	@GET
	public String heartBeat() {
		return "alive";
	}
	
}
