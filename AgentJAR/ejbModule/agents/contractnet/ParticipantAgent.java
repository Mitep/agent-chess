package agents.contractnet;

import java.util.Random;

import javax.ejb.Stateful;

import model.acl.ACLMessage;
import model.acl.Performative;
import model.agent.AID;
import model.agent.AgentClass;
import utils.MessageBuilder;

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
		if (Math.random() > 0.5) {
			System.out.println("Refusing to bid.");
			ACLMessage reply = new ACLMessage();
			reply.setPerformative(Performative.refuse);
			reply.setReceivers(new AID[] { msg.getSender() });
			MessageBuilder.sendACL(reply);
		} else {
			System.out.println("Let's bid.");
			int bid = new Random().nextInt(250);
			ACLMessage reply = new ACLMessage();
			reply.setPerformative(Performative.refuse);
			reply.setReceivers(new AID[] { msg.getSender() });
			reply.setContent(Integer.toString(bid));
			MessageBuilder.sendACL(reply);
		}
	}

	private void handleRejectProposal(ACLMessage msg) {
		System.out.print("Agent: " + Id + " -> bid rejected");
	}

	private void handleAcceptProposal(ACLMessage msg) {
		int status = new Random().nextInt(4);
		ACLMessage reply = new ACLMessage();
		reply.setSender(Id);
		reply.setReceivers(new AID[] { msg.getSender() });

		if (status == 0) {
			reply.setPerformative(Performative.failure);
			System.out
					.print("Agent: " + Id + " -> bid was accepted, but can't confirm his bid, resulting in a failure.");
		} else {
			reply.setPerformative(Performative.inform);
			System.out.print("Agent: " + Id + " -> bid was accepted and confirmed, resulting in success.");
		}

		MessageBuilder.sendACL(reply);
	}
}
