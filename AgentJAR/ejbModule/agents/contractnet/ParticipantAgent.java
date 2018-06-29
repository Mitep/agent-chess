package agents.contractnet;

import javax.ejb.Stateful;

import model.acl.ACLMessage;
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
			handleCFP(poruka);
			break;
		case reject_proposal:
			handleRejectProposal(poruka);
			break;
		case accept_proposal:
			handleAcceptProposal(poruka);
			break;
		default:
			System.out.println("Unexpected message!");

		}

	}

	private void handleCFP(ACLMessage msg) {

	}

	private void handleRejectProposal(ACLMessage msg) {

	}

	private void handleAcceptProposal(ACLMessage msg) {

	}
}
