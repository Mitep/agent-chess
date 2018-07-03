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
import model.chess.FigureCalculation;
import node_manager.NodeManagerLocal;
import utils.MessageBuilder;

public class ChessMasterAgent extends AgentClass {

	private AID player;
	
	// broj polja , tip figure
	private HashMap<Integer, String> chessTable = new HashMap<Integer, String>();
	
	// aid, trenutna pozicija
	private HashMap<AID, Integer> figureAIDs = new HashMap<AID, Integer>();
	
	private ArrayList<FigureCalculation> calcMoves = new ArrayList<FigureCalculation>();
	
	@Override
	public void handleMessage(ACLMessage msg) {
		if(msg.getPerformative().equals(Performative.request)) {
			// player trazi da inicijalizujemo igru
			initChessGame(msg);
		} else if(msg.getPerformative().equals(Performative.inform)) {
			// player nam salje koji potez je odigrao
			// sada smo mi na redu
			calculateMovement(msg);
		} else if(msg.getPerformative().equals(Performative.inform_ref)) {
			// od figura nam stizu proracuni
			getCalculations(msg);
		} else if(msg.getPerformative().equals(Performative.failure)) {
			// player nam salje da je izgubio
		}
	}

	private void initChessGame(ACLMessage playerMsg) {
		try {
			Context context = new InitialContext();
			AgentManagerLocal aml = (AgentManagerLocal) context.lookup(AgentManagerLocal.LOOKUP);
			NodeManagerLocal nml = (NodeManagerLocal) context.lookup(NodeManagerLocal.LOOKUP);
			
			AgentCenter host = nml.getThisNode();
			AgentType figureType = aml.getAgentType("ChessFigureAgent", "agents.chess");
		
			AID figureAgent;
			this.player = playerMsg.getSender();
			
			// poruka koja se salje je skoro uvek ista
			// menja se samo tip agenta kome saljemo i nejgova pozicija
			ACLMessage aclm = new ACLMessage();
			aclm.setSender(Id);
			aclm.setPerformative(Performative.request);
			
			// ========================== PAWNS =================================
			for(int i = 8; i < 16; i ++) {
				figureAgent = new AID("Pawn" + (i-7), host, figureType);
				aml.startAgent(figureAgent);
				figureAIDs.put(figureAgent, i);
				
				aclm.setReceivers(new AID[] { figureAgent });
				aclm.setContent(String.valueOf(i));
				aclm.setOntology("P");
				MessageBuilder.sendACL(aclm);
				
				chessTable.put(i, "cP");
			}
			// =========================== TOP 1 ==============================
			figureAgent = new AID("Rook1", host, figureType);
			aml.startAgent(figureAgent);
			figureAIDs.put(figureAgent, 0);
			
			aclm.setReceivers(new AID[] { figureAgent });
			aclm.setContent("0");
			aclm.setOntology("R");
			MessageBuilder.sendACL(aclm);
			
			chessTable.put(0, "cR");
			
			// =========================== TOP 2 ==============================
			figureAgent = new AID("Rook2", host, figureType);
			aml.startAgent(figureAgent);
			figureAIDs.put(figureAgent, 7);
			
			aclm.setReceivers(new AID[] { figureAgent });
			aclm.setContent("7");
			aclm.setOntology("R");
			MessageBuilder.sendACL(aclm);
			
			chessTable.put(7, "cR");
			
			// =========================== KONJ 1 ==============================
			figureAgent = new AID("Knight1", host, figureType);
			aml.startAgent(figureAgent);
			figureAIDs.put(figureAgent, 1);
			
			aclm.setReceivers(new AID[] { figureAgent });
			aclm.setContent("1");
			aclm.setOntology("N");
			MessageBuilder.sendACL(aclm);
			
			chessTable.put(1, "cN");
			
			// =========================== KONJ 2 ==============================
			figureAgent = new AID("Knight2", host, figureType);
			aml.startAgent(figureAgent);
			figureAIDs.put(figureAgent, 6);
			
			aclm.setReceivers(new AID[] { figureAgent });
			aclm.setContent("6");
			aclm.setOntology("N");
			MessageBuilder.sendACL(aclm);
			
			chessTable.put(6, "cN");
			
			// =========================== LOVAC 1 ==============================
			figureAgent = new AID("Bishop1", host, figureType);
			aml.startAgent(figureAgent);
			figureAIDs.put(figureAgent, 2);
			
			aclm.setReceivers(new AID[] { figureAgent });
			aclm.setContent("2");
			aclm.setOntology("B");
			MessageBuilder.sendACL(aclm);
			
			chessTable.put(2, "cB");
						
			// =========================== LOVAC 2 ==============================
			figureAgent = new AID("Bishop2", host, figureType);
			aml.startAgent(figureAgent);
			figureAIDs.put(figureAgent, 5);
			
			aclm.setReceivers(new AID[] { figureAgent });
			aclm.setContent("5");
			aclm.setOntology("B");
			MessageBuilder.sendACL(aclm);
			
			chessTable.put(5, "cB");
			
			// =========================== KRALJICA ==============================
			figureAgent = new AID("Queen", host, figureType);
			aml.startAgent(figureAgent);
			figureAIDs.put(figureAgent, 3);
			
			aclm.setReceivers(new AID[] { figureAgent });
			aclm.setContent("3");
			aclm.setOntology("Q");
			MessageBuilder.sendACL(aclm);
			
			chessTable.put(3, "cQ");
			
			// =========================== KRALJ ==============================
			figureAgent = new AID("King", host, figureType);
			aml.startAgent(figureAgent);
			figureAIDs.put(figureAgent, 4);
			
			aclm.setReceivers(new AID[] { figureAgent });
			aclm.setContent("4");
			aclm.setOntology("K");
			MessageBuilder.sendACL(aclm);
			
			chessTable.put(4, "cK");			
			
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
			
			// ============================= PRAZNA POLJA ====================
			for(int i = 16; i < 48; i++) {
				chessTable.put(i, "0");
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
	
	private void calculateMovement(ACLMessage playerMsg) {
		// proverimo da nam neka figurica nije pojedena
		// ako jeste uklonimo je iz liste  figureAIDs
		String content = playerMsg.getContent();
		String[] movement = content.split("-");
		int curMov = Integer.parseInt(movement[0]);
		int newMov = Integer.parseInt(movement[1]);
		String newPlayerPos = chessTable.get(newMov);
		
		if(newPlayerPos.charAt(0) == 'p') {
			// pojedena nam je figura
			// izbacujemo je iz liste
			for(AID a : figureAIDs.keySet()) {
				if(figureAIDs.get(a) == newMov) {
					figureAIDs.remove(a);
				}
			}
		}
		
		// pomerimo figuru
		String figure = chessTable.get(curMov);
		chessTable.replace(curMov, "0");
		chessTable.replace(newMov, figure);
		
		// saljemo svima sta je korisnik odigrao
		// trazimo da nam sracunaju najbolji potez
		for(AID aid : figureAIDs.keySet()) {
			ACLMessage acl = new ACLMessage();
			acl.setReceivers(new AID[] { aid });
			acl.setSender(Id);
			acl.setPerformative(Performative.propose);
			acl.setContent(playerMsg.getContent());
			MessageBuilder.sendACL(acl);
			System.out.println("poslao poruku na " + aid.getName());
		}
	}
	
	private void getCalculations(ACLMessage agentMsg) {
		// dodamo kalkulaciju u listu calcMoves
		FigureCalculation fc = new FigureCalculation();
		fc.setFigure(agentMsg.getSender());
		
		int cur_pos = (int) agentMsg.getUserArgs().get("current_position");
		fc.setCurrent_position(cur_pos);
		
		int new_pos = (int) agentMsg.getUserArgs().get("new_position");
		fc.setNew_position(new_pos);
		
		double eff = (double) agentMsg.getUserArgs().get("efficiency");
		fc.setEfficiency(eff);
		
		calcMoves.add(fc);
		
		System.out.println("stigla kalkulacija od " + agentMsg.getSender().getName());
		//System.out.println("calc size " + calcMoves.size() + " figure " + figureAIDs.size());
		// ako nam je poslednji inform stigao onda krecemo onda trazimo najbolji proracun
		if(calcMoves.size() == figureAIDs.size()) {
			moveFigure();
		}
	}
	
	private void moveFigure() {
		// pronadjemo najbolji potez
		FigureCalculation best = calcMoves.get(0);
		for(int i=1; i < calcMoves.size(); i++) {
			if(calcMoves.get(i).getEfficiency() >= best.getEfficiency())
				best = calcMoves.get(i);
		}
		
		// pomerimo figuru
		String figure = chessTable.get(best.getCurrent_position());
		chessTable.replace(best.getCurrent_position(), "0");
		chessTable.replace(best.getNew_position(), figure);
		
		// posaljemo poruku playeru i nasim agentima sta smo odigrali
		ACLMessage msg = new ACLMessage();
		msg.setSender(Id);
		msg.setPerformative(Performative.inform);
		msg.setContent(best.getCurrent_position() + "-" + best.getNew_position());
		
		System.out.println("sa pozicije " + best.getCurrent_position() + 
				" na poziciju " + best.getNew_position() + 
				" sa efikasnoscu " + best.getEfficiency());
		
		
		AID[] moveRecs = new AID[ figureAIDs.size() + 1 ];
		int i = 0;
		for(AID a : figureAIDs.keySet()) {
			moveRecs[i] = a;
			i++;
		}
		moveRecs[i] = this.player;

		msg.setReceivers( moveRecs );

		MessageBuilder.sendACL(msg);
		// ocistimo calcMoves
		calcMoves.clear();
		
		printChessTable();
	}

	private void printChessTable() {
		System.out.println("#########################################");
		for(int i = 0; i < 8; i++) {
			System.out.println("# " + getFigureChar(i*8 + 0) + " | " + 
					getFigureChar(i*8 + 1) + " | " + 
					getFigureChar(i*8 + 2) + " | " + 
					getFigureChar(i*8 + 3) + " | " + 
					getFigureChar(i*8 + 4) + " | " + 
					getFigureChar(i*8 + 5) + " | " + 
					getFigureChar(i*8 + 6) + " | " + 
					getFigureChar(i*8 + 7) + " #");
			if(i != 7)
				System.out.println("#---------------------------------------#");
		}
		System.out.println("#########################################");
	}
	
	private String getFigureChar(int loc) {
		if(chessTable.get(loc).equals("0"))
			return "0 ";
		else
			return chessTable.get(loc);
	}
	
	
}
