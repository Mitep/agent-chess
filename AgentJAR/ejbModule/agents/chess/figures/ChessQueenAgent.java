package agents.chess.figures;

import java.util.HashMap;

import model.acl.ACLMessage;
import model.acl.Performative;
import model.agent.AID;
import model.agent.AgentClass;
import utils.MessageBuilder;

public class ChessQueenAgent extends AgentClass {

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

			// u sustini kombinacija topa i lovca
			// prvo lovac
			// dijagonala - gore levo
			int dx = 1;
			int dy = 1;
			while ((x - dx) >= 0 && (y - dy) >= 0) {
				if (niz[(x - dx) * 8 + (y - dy)].equals("0")) {
					result[(x - dx) * 8 + (y - dy)] = 1;
				} else if (niz[(x - dx) * 8 + (y - dy)].charAt(0) == 'p') {
					result[(x - dx) * 8 + (y - dy)] = 1;
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
				} else if (niz[(x - dx) * 8 + (y + dy)].charAt(0) == 'p') {
					result[(x - dx) * 8 + (y + dy)] = 1;
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
				} else if (niz[(x + dx) * 8 + (y - dy)].charAt(0) == 'p') {
					result[(x + dx) * 8 + (y - dy)] = 1;
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
				} else if (niz[(x + dx) * 8 + (y - dy)].charAt(0) == 'p') {
					result[(x + dx) * 8 + (y - dy)] = 1;
					break;
				}
				dx++;
				dy++;
			}

			// pa top
			int xdmax = 7 - x; // koliko se moze kretati dole po x osi do kraja table
			int ylmax = y; // koliko se moze kretati ulevo po y osi pre nego sto dodje do kraja table, itd.
			int xumax = x;
			int ydmax = 7 - y;

			for (int i = 1; i <= xdmax; i++) {
				if (niz[(x + i) * 8 + y].equals("0")) {
					result[(x + i) * 8 + y] = 1;
				} else if (niz[(x + i) * 8 + y].charAt(0) == 'p') {
					result[(x + i) * 8 + y] = 1;
					break;
				}
			}

			for (int i = 1; i <= xumax; i++) {
				if (niz[(x - i) * 8 + y].equals("0")) {
					result[(x - i) * 8 + y] = 1;
				} else if (niz[(x - i) * 8 + y].charAt(0) == 'p') {
					result[(x - i) * 8 + y] = 1;
					break;
				}
			}

			for (int i = 1; i <= ydmax; i++) {
				if (niz[x * 8 + (y + i)].equals("0")) {
					result[x * 8 + (y + i)] = 1;
				} else if (niz[x * 8 + (y + i)].charAt(0) == 'p') {
					result[x * 8 + (y + i)] = 1;
					break;
				}
			}

			for (int i = 1; i <= ylmax; i++) {
				if (niz[x * 8 + (y - i)].equals("0")) {
					result[x * 8 + (y - i)] = 1;
				} else if (niz[x * 8 + (y - i)].charAt(0) == 'p') {
					result[x * 8 + (y - i)] = 1;
					break;
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
