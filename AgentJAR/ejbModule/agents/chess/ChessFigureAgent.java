package agents.chess;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.Stateful;
import javax.naming.Context;
import javax.naming.InitialContext;

import agent_manager.AgentManagerLocal;
import model.acl.ACLMessage;
import model.acl.Performative;
import model.agent.AID;
import model.agent.AgentClass;
import model.agent.AgentType;
import model.center.AgentCenter;
import utils.MessageBuilder;

@Stateful
public class ChessFigureAgent extends AgentClass {

	public static double pawnValue = 1.5;
	public static double knightValue = 3.3;
	public static double bishopValue = 3.3;
	public static double rookValue = 5.25;
	public static double queenValue = 10.0;
	public static double kingValue = 200.0;
	
	private String figureType;
	private int currentPosition;
	private HashMap<Integer, String> chessTable = new HashMap<Integer, String>();
	private ArrayList<Double> utilResult = new ArrayList<Double>();
	private int enemyNumber;
	private int returnedResults = 0;
	
	private HashMap<AgentType, AID> utilAgents = new HashMap<AgentType, AID>();
	
	private AID master;
	
	@Override
	public void handleMessage(ACLMessage msg) {
		if(msg.getPerformative().equals(Performative.request)) {
			// inizijalizacija agenta i tabele
			initAgent(msg);
		} else if (msg.getPerformative().equals(Performative.propose)) {
			// racunamo najbolji potez
			// vracamo inform_ref
			calculateMovement(msg);
		} else if (msg.getPerformative().equals(Performative.inform_ref)) {
			// stize nam od util agenta poruka
			getUtilResult(msg);
		}  else if (msg.getPerformative().equals(Performative.inform)) {
			// salje nam master koji potez je odigrao
			playersMove(msg);
		}
	}
	
	private void initAgent(ACLMessage masterMsg) {
		master = masterMsg.getSender();
		figureType = masterMsg.getOntology();
		currentPosition = Integer.parseInt(masterMsg.getContent());
		
		this.enemyNumber = 16;
		// inicijalizacija tabele
		// nasi
			chessTable.put(0, "cR");
			chessTable.put(1, "cN");
			chessTable.put(2, "cB");
			chessTable.put(3, "cQ");
			chessTable.put(4, "cK");
			chessTable.put(5, "cB");
			chessTable.put(6, "cN");
			chessTable.put(7, "cR");
			for(int i= 8; i < 16 ; i++) {
				chessTable.put(i, "cP");
			}
		// korisnikovi
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
		
		//System.out.println("======================================== " + Id.getName() + " ==================================");
		//printChessTable();
	}

	private void calculateMovement(ACLMessage masterMsg) {
		// unesemo promene koje nam je korisnik poslao
		String[] movement = masterMsg.getContent().split("-");
		int curMov = Integer.parseInt(movement[0]);
		int newMov = Integer.parseInt(movement[1]);
		
		// pomerimo figuru
		String figure = chessTable.get(curMov);
		chessTable.replace(curMov, "0");
		chessTable.replace(newMov, figure);
		
		//System.out.println("======================================== " + Id.getName() + " ==================================");
		//printChessTable();
		
		AgentCenter host = masterMsg.getSender().getHost();
		AID agentUtil;
		ACLMessage messageUtil = new ACLMessage();
		messageUtil.setPerformative(Performative.request);
		// racunamo
		// prvo odredimo gde mi mozemo da idemo
		agentUtil = getChessUtilAgent(figureType.charAt(0), host);
		messageUtil.setReceivers(new AID[] { agentUtil });
		messageUtil.setSender(Id);
		HashMap<String, Object> userArgs = new HashMap<String, Object>();
		userArgs.put("position", currentPosition);
		
		String[] tableArr = new String[64];
		for(int i = 0 ; i < 64 ; i++) {
			tableArr[i] = chessTable.get(i);
		}
		userArgs.put("table", tableArr);
		
		messageUtil.setUserArgs(userArgs);
		
		System.out.println(figureType + " poziva " + agentUtil.getName() + " da izracuna kuda moze da se krece");
		
		MessageBuilder.sendACL(messageUtil);
		
		// za svaku protivnicku figuru odredimo da li moze da nas pojede ili ne
		// dodamo u rezultat tako sto ga umanjimo
//		for(int i = 0; i < 64; i++) {
//			if(chessTable.get(i).charAt(0) == 'p') {
//				agentUtil = getChessUtilAgent(chessTable.get(i).charAt(1), host);
//				messageUtil.setReceivers(new AID[] { agentUtil });
//				messageUtil.setSender(Id);
//				userArgs.replace("position", i);
//				MessageBuilder.sendACL(messageUtil);
//			}
//		}
		
	}
	
	private void getUtilResult(ACLMessage utilMsg) {
		HashMap<String, Object> userArgs = utilMsg.getUserArgs();
		int[] result = (int[]) userArgs.get("result");
		
		if(result[currentPosition] == -1) {
			// za ovu figuru
			for(int i = 0; i < 64; i++) {
				if(i == currentPosition) {
					utilResult.add(-0.5);
					continue;
				}
				
				if(chessTable.get(i).charAt(0) == 'p') {
					switch(chessTable.get(i).charAt(1)) {
						case 'P': utilResult.add(((double) result[i]) * pawnValue);
								break;
						case 'N': utilResult.add(((double) result[i]) * knightValue);
								break;
						case 'B': utilResult.add(((double) result[i]) * bishopValue);
								break;
						case 'R': utilResult.add(((double) result[i]) * rookValue);
								break;
						case 'Q': utilResult.add(((double) result[i]) * queenValue);
								break;
						case 'K': utilResult.add(((double) result[i]) * kingValue);
								break;
					}
				} else {
					utilResult.add((double) result[i]);
				}
			}
		} else {
			// gledamo za protivnicke figure
			// moramo implementirati
			returnedResults++;
//			
//			ArrayList<Double> newUtilResult = new ArrayList<Double>();
//
//			for(int i=0; i < 64; i++) {
//				if(result[i] == 1) {
//					newUtilResult.add(utilResult.get(i) - 0.5);
//				} else {
//					newUtilResult.add(utilResult.get(i));
//				}
//			}
//			utilResult = newUtilResult;
		}
		
		// ako su nam svi rezultati vraceni onda saljemo rezultat nasem master agentu
		if(returnedResults == enemyNumber) {
			sendResults();
		}
		
		sendResults();
	}
	
	private void sendResults() {
		ACLMessage retMsg = new ACLMessage();
		retMsg.setReceivers(new AID[] { master });
		retMsg.setSender(Id);
			
		int newPos = 0;
		double eff = -1;
		for(int i = 1; i < 64; i ++) {
			if(utilResult.get(i) > eff) {
				eff = utilResult.get(i);
				newPos = i;
			}
		}
		HashMap<String, Object> userArgs = new HashMap<String, Object>();
		userArgs.put("current_position", currentPosition);
		userArgs.put("new_position", newPos);
		userArgs.put("efficiency", eff);
		retMsg.setUserArgs(userArgs);
		retMsg.setPerformative(Performative.inform_ref);
		MessageBuilder.sendACL(retMsg);
		
		returnedResults = 0;
		utilResult.clear();
	}
	
	private void playersMove(ACLMessage playerMsg) {
		String[] movement = playerMsg.getContent().split("-");
		int curPos = Integer.parseInt(movement[0]);
		int newPos = Integer.parseInt(movement[1]);
		
		if(chessTable.get(newPos).charAt(0) == 'p') {
			enemyNumber--;
		}
		chessTable.replace(newPos, chessTable.get(curPos));
		chessTable.replace(curPos, "0");
		
		if(currentPosition == curPos) {
			currentPosition = newPos;
		}
	}
	
	private AID getChessUtilAgent(char aType, AgentCenter host) {
		try {
			Context context = new InitialContext();
			AgentManagerLocal aml = (AgentManagerLocal) context.lookup(AgentManagerLocal.LOOKUP);
			
			AgentType figureType = null;
			switch(aType) {
				case 'P': figureType = aml.getAgentType("ChessPawnAgent", "agents.chess.figures");
						break;
				case 'N': figureType = aml.getAgentType("ChessKnightAgent", "agents.chess.figures");
						break;
				case 'B': figureType = aml.getAgentType("ChessBishopAgent", "agents.chess.figures");
						break;
				case 'R': figureType = aml.getAgentType("ChessRookAgent", "agents.chess.figures");
						break;
				case 'Q': figureType = aml.getAgentType("ChessQueenAgent", "agents.chess.figures");
						break;
				case 'K': figureType = aml.getAgentType("ChessKingAgent", "agents.chess.figures");
						break;
			}
			
			
			AID retAgent = null;
			for(AgentType a : utilAgents.keySet()) {
				if(a.getName().equals(figureType.getName())) {
					retAgent = utilAgents.get(a);
					return utilAgents.get(a);
				}
			}
			
			
			if(retAgent == null) {
				String agentName = aType + "util"+ currentPosition;
				retAgent = new AID(agentName, host, figureType);
				aml.startAgent(retAgent);
				utilAgents.put(figureType, retAgent);
			}			
			
			return retAgent;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
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
