package agents.chess;

import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;

import agent_manager.AgentManagerLocal;
import model.acl.ACLMessage;
import model.acl.Performative;
import model.agent.AID;
import model.agent.AgentClass;
import model.agent.AgentType;
import model.center.AgentCenter;
import node_manager.NodeManagerLocal;
import utils.MessageBuilder;

public class ChessMasterAgent extends AgentClass {

	// broj polja , tip figure
	private HashMap<Integer, String> chessTable = new HashMap<Integer, String>();
	
	private ArrayList<AID> figureAIDs = new ArrayList<AID>();
	
	@Override
	public void handleMessage(ACLMessage msg) {
		if(msg.getPerformative().equals(Performative.request)) {
			// player trazi da inicijalizujemo igru
			initChessGame(msg);
		} else if(msg.getPerformative().equals(Performative.inform)) {
			// player nam salje koji potez je odigrao
			// sada smo mi na redu
			moveFigure();
		}
	}
	
	private void initChessGame(ACLMessage playerMsg) {
		try {
			Context context = new InitialContext();
			AgentManagerLocal aml = (AgentManagerLocal) context.lookup(AgentManagerLocal.LOOKUP);
			NodeManagerLocal nml = (NodeManagerLocal) context.lookup(NodeManagerLocal.LOOKUP);
			
			AgentCenter host = nml.getThisNode();
			AgentType figureType = aml.getAgentType("ChessFigureAgent", "chess.ChessFigureAgent");
		
			AID figureAgent;
			
			// poruka koja se salje je skoro uvek ista
			// menja se samo tip agenta kome saljemo i nejgova pozicija
			ACLMessage aclm = new ACLMessage();
			aclm.setSender(Id);
			aclm.setPerformative(Performative.request);
			
			// ========================== PAWN =================================
			for(int i = 8; i < 16; i ++) {
				figureAgent = new AID("Pawn" + (i-7), host, figureType);
				aml.startAgent(figureAgent);
				figureAIDs.add(figureAgent);
				
				aclm.setReceivers(new AID[] { figureAgent });
				aclm.setContent(String.valueOf(i));
				aclm.setOntology("Rook");
				MessageBuilder.sendACL(aclm);
				
				chessTable.put(i, "cP");
			}
			// =========================== TOP 1 ==============================
			figureAgent = new AID("Rook1", host, figureType);
			aml.startAgent(figureAgent);
			figureAIDs.add(figureAgent);
			
			aclm.setReceivers(new AID[] { figureAgent });
			aclm.setContent("0");
			aclm.setOntology("Rook");
			MessageBuilder.sendACL(aclm);
			
			chessTable.put(0, "cR");
			
			// =========================== TOP 2 ==============================
			figureAgent = new AID("Rook2", host, figureType);
			aml.startAgent(figureAgent);
			figureAIDs.add(figureAgent);
			
			aclm.setReceivers(new AID[] { figureAgent });
			aclm.setContent("7");
			aclm.setOntology("Rook");
			MessageBuilder.sendACL(aclm);
			
			chessTable.put(7, "cR");
			
			
			// ==================== Inicijalizacija korisnikovih figurica ==================
			chessTable.put(56, "pR");
			chessTable.put(57, "pN");
			chessTable.put(58, "pB");
			chessTable.put(59, "pQ");
			chessTable.put(60, "pK");
			chessTable.put(61, "pB");
			chessTable.put(62, "pN");
			chessTable.put(63, "pR");
			for(int i= 48; i < 56 ; i++) {
				chessTable.put(i, "pP");
			}
			
			// =========================== KRAJ ===============================
			// saljemo poruku playeru da moze da zapocne igru tako sto salje prvi potez
			ACLMessage aclmp = new ACLMessage();
			aclmp.setSender(Id);
			aclmp.setPerformative(Performative.request);
			aclmp.setContent("Move figure.");
			aclmp.setReceivers(new AID[] { playerMsg.getSender() });
			MessageBuilder.sendACL(aclmp);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void moveFigure() {
		
		// ovde treba neka struktura 
		
		for(AID fig : figureAIDs) {
			
			// sracunamo za svaku figuru koja joj je najbolja opcija
			// opciju smestimo u strukturu
			
		} 
		
		// izaberemo najbolju opciju iz strukture i nju saljemo playeru
	}

}
