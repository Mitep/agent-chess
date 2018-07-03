package agents.chess.figures;

import java.util.HashMap;

import model.acl.ACLMessage;
import model.acl.Performative;
import model.agent.AID;
import model.agent.AgentClass;
import utils.MessageBuilder;

public class ChessBishopAgent extends AgentClass {

	@Override
	public void handleMessage(ACLMessage poruka) {
		if (poruka.getPerformative() == Performative.request) {
			HashMap<String, Object> userArgs = poruka.getUserArgs();
			String niz[] = (String[]) userArgs.get("table");
			int poz = (int) userArgs.get("position");

			int x = poz / 8;
			int y = (poz % 8);

			int result[] = new int[64];
			result[poz] = -1;

			char enemy = 'p';
			char friend = 'c';
			if (niz[poz].charAt(0) == 'p') { // da li je rec o igracevom pijunu
				enemy = 'c';
				friend = 'p';
			}

			// dijagonala - gore levo
			int dx = 1;
			int dy = 1;
			while ((x - dx) >= 0 && (y - dy) >= 0) {
				if (niz[(x - dx) * 8 + (y - dy)].equals("0")) {
					result[(x - dx) * 8 + (y - dy)] = 1;
				} else if (niz[(x - dx) * 8 + (y - dy)].charAt(0) == enemy) {
					result[(x - dx) * 8 + (y - dy)] = 1;
					break;
				} else if (niz[(x - dx) * 8 + (y - dy)].charAt(0) == friend) {
					break;
				}
				dx++;
				dy++;
			}

			// dijagonala - gore desno
			dx = 1;
			dy = 1;
			while ((x - dx) >= 0 && (y + dy) < 8) {
				if (niz[(x - dx) * 8 + (y + dy)].equals("0")) {
					result[(x - dx) * 8 + (y + dy)] = 1;
				} else if (niz[(x - dx) * 8 + (y + dy)].charAt(0) == enemy) {
					result[(x - dx) * 8 + (y + dy)] = 1;
					break;
				} else if (niz[(x - dx) * 8 + (y + dy)].charAt(0) == friend) {
					break;
				}
				dx++;
				dy++;
			}

			// dijagonala - dole levo
			dx = 1;
			dy = 1;
			while ((x + dx) < 8 && (y - dy) >= 0) {
				if (niz[(x + dx) * 8 + (y - dy)].equals("0")) {
					result[(x + dx) * 8 + (y - dy)] = 1;
				} else if (niz[(x + dx) * 8 + (y - dy)].charAt(0) == enemy) {
					result[(x + dx) * 8 + (y - dy)] = 1;
					break;
				} else if (niz[(x + dx) * 8 + (y - dy)].charAt(0) == friend) {
					break;
				}
				dx++;
				dy++;
			}

			// dijagonala - dole desno
			dx = 1;
			dy = 1;
			while ((x + dx) < 8 && (y + dy) < 8) {
				if (niz[(x + dx) * 8 + (y - dy)].equals("0")) {
					result[(x + dx) * 8 + (y - dy)] = 1;
				} else if (niz[(x + dx) * 8 + (y - dy)].charAt(0) == enemy) {
					result[(x + dx) * 8 + (y - dy)] = 1;
					break;
				} else if (niz[(x + dx) * 8 + (y - dy)].charAt(0) == friend) {
					break;
				}
				dx++;
				dy++;
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
