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
		}
	}
	
//	private void startGame() {
//		try {
//			Context context = new InitialContext();
//			AgentManagerLocal aml = (AgentManagerLocal) context.lookup(AgentManagerLocal.LOOKUP);
//			NodeManagerLocal nml = (NodeManagerLocal) context.lookup(NodeManagerLocal.LOOKUP);
//			
//			// kreiranje master agenta
//			AID masterAgent = new AID("master",
//					new AgentCenter(nml.getThisNode().getAddress(), nml.getThisNode().getAlias()),
//					aml.getAgentType("ChessMasterAgent", "chess.ChessMasterAgent"));
//			aml.startAgent(masterAgent);
//			
//			// slanje poruke master agentu da inicijalizuje partiju
//			ACLMessage toMaster = new ACLMessage();
//			toMaster.setReceivers(new AID[] { masterAgent });
//			toMaster.setSender(Id);
//			toMaster.setPerformative(Performative.request);
//			MessageBuilder.sendACL(toMaster);
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//	}

}
