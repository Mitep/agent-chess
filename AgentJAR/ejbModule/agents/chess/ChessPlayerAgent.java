package agents.chess;

import model.acl.ACLMessage;
import model.acl.Performative;
import model.agent.AgentClass;

public class ChessPlayerAgent extends AgentClass {

	@Override
	public void handleMessage(ACLMessage msg) {
		if(msg.getPerformative().equals(Performative.inform)) {
			// master salje useru koji potez je odigrao
			System.out.println(msg.getContent());
		} else if(msg.getPerformative().equals(Performative.request)) {
			// zapocni igru 
			System.out.println(msg.getContent());
		} else if(msg.getPerformative().equals(Performative.failure)) {
			// computer nam salje da je izgubio
		}
	}
	
}
