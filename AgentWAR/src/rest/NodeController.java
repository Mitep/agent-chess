package rest;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
	         	case "contact_master": rnl.connectSlave(JsonUtils.getNodeRequestSlaveAddres(request));
	         		break;
	         	case "": 
	         		break;
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
}
