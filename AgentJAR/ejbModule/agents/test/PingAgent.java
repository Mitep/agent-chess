package agents.test;

import javax.ejb.Stateful;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.acl.ACLMessage;
import model.acl.Performative;
import model.agent.AID;
import model.agent.AgentClass;
import model.agent.AgentType;
import model.center.AgentCenter;
import node_manager.NodeManagerLocal;
import utils.JsonUtils;

@Stateful
public class PingAgent extends AgentClass {

	@Override
	public void handleMessage(ACLMessage poruka) {
		if (poruka.getPerformative() == Performative.request) {

			AID receiver = new AID();
			receiver.setName(poruka.getContent());
			AgentType type = new AgentType(PongAgent.class.getSimpleName(), PongAgent.class.getPackage().getName());
			receiver.setType(type);
			AgentCenter host = lookupHost();
			if (host == null) {
				System.out.println("Error: Cannot locate host");
				return;
			}
			receiver.setHost(host);

			ACLMessage msg = new ACLMessage();
			msg.setPerformative(Performative.request);
			msg.setReceivers(new AID[] { receiver });
			msg.setSender(this.getId());
			msg.setContent(poruka.getContent());
			JsonUtils.sendACL(msg);
		} else if (poruka.getPerformative() == Performative.inform) {
			System.out.println("Reply received from Pong");

		}
	}

	private AgentCenter lookupHost() {
		try {
			Context ctx = new InitialContext();
			NodeManagerLocal nml = (NodeManagerLocal) ctx.lookup(NodeManagerLocal.LOOKUP);
			return nml.getThisNode();
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
