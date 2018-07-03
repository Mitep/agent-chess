package agents.chess.figures;

import java.util.HashMap;

import model.acl.ACLMessage;
import model.acl.Performative;
import model.agent.AID;
import model.agent.AgentClass;
import utils.MessageBuilder;

public class ChessRookAgent extends AgentClass {

	// x\y
	// 0	1	2	3	4	5	6	7
	// 8	9	10	11	12	13	14	15
	// 16	17	18	19	20	21	22	23
	// 24	25	26	27	28	29	30	31
	// 32	33	34	35	36	37	38	39
	// 40	41	42	43	44	45	46	47
	// 48	49	50	51	52	53	54	55
	// 56	57	58	59	60	61	62	63

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
			if (niz[poz].charAt(0) == 'p') { // da li je rec o igracevom pijunu
				enemy = 'c';
			}

			int xdmax = 7 - x; // koliko se moze kretati dole po x osi do kraja table
			int ylmax = y; // koliko se moze kretati ulevo po y osi pre nego sto dodje do kraja table, itd.
			int xumax = x;
			int ydmax = 7 - y;

			for (int i = 1; i <= xdmax; i++) {
				if (niz[(x + i) * 8 + y].equals("0")) {
					result[(x + i) * 8 + y] = 1;
				} else if (niz[(x + i) * 8 + y].charAt(0) == enemy) {
					result[(x + i) * 8 + y] = 1;
					break;
				}
			}

			for (int i = 1; i <= xumax; i++) {
				if (niz[(x - i) * 8 + y].equals("0")) {
					result[(x - i) * 8 + y] = 1;
				} else if (niz[(x - i) * 8 + y].charAt(0) == enemy) {
					result[(x - i) * 8 + y] = 1;
					break;
				}
			}

			for (int i = 1; i <= ydmax; i++) {
				if (niz[x * 8 + (y + i)].equals("0")) {
					result[x * 8 + (y + i)] = 1;
				} else if (niz[x * 8 + (y + i)].charAt(0) == enemy) {
					result[x * 8 + (y + i)] = 1;
					break;
				}
			}

			for (int i = 1; i <= ylmax; i++) {
				if (niz[x * 8 + (y - i)].equals("0")) {
					result[x * 8 + (y - i)] = 1;
				} else if (niz[x * 8 + (y - i)].charAt(0) == enemy) {
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
