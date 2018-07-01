package agents.contractnet;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import agent_manager.AgentManagerLocal;
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
public class InitiatorAgent extends AgentClass {

	public static long REPLY_BY = 10;
	public static int MIN_BID = 100;

	private List<AID> participants;
	private boolean inProgress = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.agent.AgentInterface#handleMessage(model.acl.ACLMessage)
	 */
	@Override
	public void handleMessage(ACLMessage poruka) {

		if (poruka.getPerformative() == Performative.request && inProgress == false) {
			inProgress = true;
			participants = getParticipants();
			if (participants.isEmpty()) {
				System.out.println("Error: No participants. Exiting now.");
				return;
			}
			sendCFP((AID[]) participants.toArray());
		} else if (inProgress) {
			switch (poruka.getPerformative()) {
			case propose:
				handlePropose(poruka);
				break;
			case refuse:
				handleRefuse(poruka);
				break;
			case inform:
				handleInform(poruka);
				break;
			case failure:
				handleFailure(poruka);
				break;
			default:
				System.out.println("Unexpected message.");
			}
		}
	}

	private List<AID> getParticipants() {
		Context ctx;
		try {
			ctx = new InitialContext();
			AgentManagerLocal manager = (AgentManagerLocal) ctx.lookup(AgentManagerLocal.LOOKUP);
			ArrayList<AID> retVal = new ArrayList<>();
			for (AID id : manager.getRunningAgents()) {
				if (id.getType().getName().contains("InitiatorAgent")) {
					retVal.add(id);
				}
			}
			return retVal;
		} catch (NamingException e) {
			e.printStackTrace();
			return new ArrayList<AID>();
		}
	}

	private void sendCFP(AID[] receivers) {
		ACLMessage cfpMsg = new ACLMessage();
		cfpMsg.setPerformative(Performative.cfp);
		cfpMsg.setReplyBy(REPLY_BY);
		cfpMsg.setReceivers(receivers);
		cfpMsg.setSender(Id);
		MessageBuilder.sendACL(cfpMsg);
	}

	private void handlePropose(ACLMessage msg) {
		int bid = Integer.parseInt(msg.getContent());
		if (bid < MIN_BID) {
			System.out.println("Bid by agent " + msg.getSender() + " too small. Rejecting.");
			ACLMessage reply = new ACLMessage();
			reply.setPerformative(Performative.reject_proposal);
			reply.setReceivers(new AID[] { msg.getSender() });
			MessageBuilder.sendACL(reply);
		} else {
			System.out.println("Bid by agent " + msg.getSender() + " ok. Accepting.");
			ACLMessage reply = new ACLMessage();
			reply.setPerformative(Performative.accept_proposal);
			reply.setReceivers(new AID[] { msg.getSender() });
			MessageBuilder.sendACL(reply);
		}

	}

	private void handleInform(ACLMessage msg) {

	}

	private void handleRefuse(ACLMessage msg) {
		System.out.println("Agent -> " + msg.getSender() + " refused.");
		System.out.println(participants.size());
		participants.remove(msg.getSender());
		System.out.println(participants.size());
	}

	private void handleFailure(ACLMessage msg) {
		System.out.println("Agent -> " + msg.getSender() + " failed.");
		//inProgress = false;
	}

}
