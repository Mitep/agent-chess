package agents.contractnet;

import javax.ejb.Stateful;

import model.acl.ACLMessage;
import model.acl.Performative;
import model.agent.AgentClass;

/**
 * @author Nikola
 *
 */
@Stateful
public class ParticipantAgent extends AgentClass {

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.agent.AgentInterface#handleMessage(model.acl.ACLMessage)
	 */
	@Override
	public void handleMessage(ACLMessage poruka) {
		// TODO Auto-generated method stub
		switch (poruka.getPerformative()) {
		case cfp:

			break;
		case reject_proposal:

			break;
		case accept_proposal:

			break;
		default:

		}

	}

	private void handleCFP(ACLMessage msg) {

	}

	private void handleRejectProposal(ACLMessage msg) {

	}

	private void handleAcceptProposal(ACLMessage msg) {

	}
}
