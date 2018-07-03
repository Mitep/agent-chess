package agents.chess.figures;

import java.util.HashMap;

import model.acl.ACLMessage;
import model.acl.Performative;
import model.agent.AID;
import model.agent.AgentClass;
import utils.MessageBuilder;

public class ChessPawnAgent extends AgentClass {

	@Override
	public void handleMessage(ACLMessage poruka) {
		if (poruka.getPerformative() == Performative.request) {
			HashMap<String, Object> userArgs = poruka.getUserArgs();
			String niz[] = (String[]) userArgs.get("table");
			int poz = (int) userArgs.get("position");

			int direction = 1;
			if (niz[poz].charAt(0) == 'p') { // da li je rec o igracevom pijunu
				direction = -1;
			}

			int x = poz / 8;
			int y = (poz % 8);

			int result[] = new int[64];
			result[poz] = -1;

			// pomeranje za jedno polje unapred
			// proverava se da li je "napred" dole
			if (direction == 1) {
				// proverava da li je figura u zadnjem redu
				if (x < 7) {
					if (niz[(x + direction) * 8 + y].equals("0")) {
						result[(x + direction) * 8 + y] = 1;
					}

					// figura se ne nalazi u krajnjem levom redu
					if (y > 0) {
						String pos = niz[(x + direction) * 8 + (y - 1)];
						if (pos.charAt(0) == 'p') {
							result[(x + direction) * 8 + (y - 1)] = 1;
						}
					}

					// figura se ne nalazi u samom desnom redu
					if (y < 7) {
						String pos = niz[(x + direction) * 8 + (y + 1)];
						if (pos.charAt(0) == 'p') {
							result[(x + direction) * 8 + (y + 1)] = 1;
						}
					}
				}
			} else {
				// proverava da li je figura u zadnjem redu
				if (x > 0) {
					if (niz[(x + direction) * 8 + y].equals("0")) {
						result[(x + direction) * 8 + y] = 1;
					}

					// figura se ne nalazi u krajnjem levom redu
					if (y > 0) {
						String pos = niz[(x + direction) * 8 + (y - 1)];
						if (pos.charAt(0) == 'p') {
							result[(x + direction) * 8 + (y - 1)] = 1;
						}
					}

					// figura se ne nalazi u samom desnom redu
					if (y < 7) {
						String pos = niz[(x + direction) * 8 + (y + 1)];
						if (pos.charAt(0) == 'p') {
							result[(x + direction) * 8 + (y + 1)] = 1;
						}
					}
				}
			}

			ACLMessage reply = new ACLMessage();
			HashMap<String, Object> replyArgs = new HashMap<>();
			replyArgs.put("result", result);
			reply.setPerformative(Performative.inform_ref);
			reply.setUserArgs(replyArgs);
			reply.setSender(Id);
			reply.setReceivers(new AID[] { poruka.getSender() });
			MessageBuilder.sendACL(reply);
		} else {
			System.out.println("Error: Unexpected message.");
		}
	}

}
