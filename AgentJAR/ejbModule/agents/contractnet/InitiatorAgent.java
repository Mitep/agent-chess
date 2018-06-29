package agents.contractnet;

import javax.ejb.Stateful;

import model.acl.ACLMessage;
import model.agent.AgentClass;

/**
 * @author Nikola
 *
 */
@Stateful
public class InitiatorAgent extends AgentClass {

	/* (non-Javadoc)
	 * @see model.agent.AgentInterface#handleMessage(model.acl.ACLMessage)
	 */
	@Override
	public void handleMessage(ACLMessage poruka) {
		// TODO Auto-generated method stub
	}

}
