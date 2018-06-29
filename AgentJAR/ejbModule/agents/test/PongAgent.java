package agents.test;

import javax.ejb.Stateful;

import model.acl.ACLMessage;
import model.acl.Performative;
import model.agent.AID;
import model.agent.AgentClass;
import utils.JsonUtils;

@Stateful
public class PongAgent extends AgentClass {

	@Override
	public void handleMessage(ACLMessage poruka) {
		if (poruka.getPerformative() == Performative.request) {
			ACLMessage response = new ACLMessage();
			response.setReceivers(new AID[] { poruka.getSender() });
			response.setPerformative(Performative.inform);
			response.setContent("Message received from Ping");
			JsonUtils.sendACL(response);
		}

	}

}
