package agents.chess.figures;

import java.util.HashMap;

import model.acl.ACLMessage;
import model.acl.Performative;
import model.agent.AID;
import model.agent.AgentClass;
import utils.MessageBuilder;

public class ChessKnightAgent extends AgentClass {

	@Override
	public void handleMessage(ACLMessage poruka) {
		if (poruka.getPerformative() == Performative.request) {
			HashMap<String, Object> userArgs = poruka.getUserArgs();
			String niz[] = (String[]) userArgs.get("table");
			int poz = (int) userArgs.get("position");

			char enemy = 'p';
			if (niz[poz].charAt(0) == 'p') { // da li je rec o igracevom pijunu
				enemy = 'c';
			}
			
			int x = poz / 8;
			int y = (poz % 8);

			int result[] = new int[64];
			result[poz] = -1;

			if (x == 0 && y == 0) {
				// konj se nalazi u gornjem levom cosku
				if (niz[17].equals("0") || niz[17].charAt(0) == enemy) {
					result[17] = 1;
				}

				if (niz[10].equals("0") || niz[10].charAt(0) == enemy) {
					result[10] = 1;
				}
			} else if (x == 7 && y == 7) {

				// konj se nalazi u donjem desnom cosku
				if (niz[53].equals("0") || niz[53].charAt(0) == enemy) {
					result[53] = 1;
				}

				if (niz[46].equals("0") || niz[46].charAt(0) == enemy) {
					result[46] = 1;
				}

			} else if (x == 7 && y == 0) {

				// konj se nalazi u donjem levom cosku
				if (niz[41].equals("0") || niz[41].charAt(0) == enemy) {
					result[41] = 1;
				}

				if (niz[50].equals("0") || niz[50].charAt(0) == enemy) {
					result[50] = 1;
				}
			} else if (x == 0 && y == 7) {

				// konj se nalazi u gornjem desnom cosku
				if (niz[13].equals("0") || niz[13].charAt(0) == enemy) {
					result[13] = 1;
				}

				if (niz[22].equals("0") || niz[22].charAt(0) == enemy) {
					result[22] = 1;
				}

			} else if (x == 1 && y == 1) {

				// niz[9]
				if (niz[3].equals("0") || niz[3].charAt(0) == enemy) {
					result[3] = 1;
				}

				if (niz[19].equals("0") || niz[19].charAt(0) == enemy) {
					result[19] = 1;
				}

				if (niz[24].equals("0") || niz[24].charAt(0) == enemy) {
					result[24] = 1;
				}

				if (niz[26].equals("0") || niz[26].charAt(0) == enemy) {
					result[26] = 1;
				}

			} else if (x == 1 && y == 6) {

				// niz[14]
				if (niz[4].equals("0") || niz[4].charAt(0) == enemy) {
					result[4] = 1;
				}

				if (niz[20].equals("0") || niz[20].charAt(0) == enemy) {
					result[20] = 1;
				}

				if (niz[29].equals("0") || niz[29].charAt(0) == enemy) {
					result[29] = 1;
				}

				if (niz[31].equals("0") || niz[31].charAt(0) == enemy) {
					result[31] = 1;
				}

			} else if (x == 6 && y == 1) {

				// niz[49]
				if (niz[32].equals("0") || niz[32].charAt(0) == enemy) {
					result[32] = 1;
				}

				if (niz[34].equals("0") || niz[34].charAt(0) == enemy) {
					result[34] = 1;
				}

				if (niz[43].equals("0") || niz[43].charAt(0) == enemy) {
					result[43] = 1;
				}

				if (niz[59].equals("0") || niz[59].charAt(0) == enemy) {
					result[59] = 1;
				}

			} else if (x == 6 && y == 54) {

				// niz[54]
				if (niz[60].equals("0") || niz[60].charAt(0) == enemy) {
					result[60] = 1;
				}

				if (niz[44].equals("0") || niz[44].charAt(0) == enemy) {
					result[44] = 1;
				}

				if (niz[37].equals("0") || niz[37].charAt(0) == enemy) {
					result[37] = 1;
				}

				if (niz[39].equals("0") || niz[39].charAt(0) == enemy) {
					result[39] = 1;
				}

			} else if (x == 0) {

				for (int dx = 1; dx <= 2; dx++) {
					for (int dy = 2; dy >= 1; dy--) {
						if (dy != dx) {
							break;
						}

						if (niz[dx * 8 + y + dy].equals("0") || niz[dx * 8 + y + dy].charAt(0) == enemy) {
							result[dx * 8 + y + dy] = 1;
						}

						if (niz[dx * 8 + y - dy].equals("0") || niz[dx * 8 + y - dy].charAt(0) == enemy) {
							result[dx * 8 + y - dy] = 1;
						}

					}
				}

			} else if (y == 0) {

				for (int dy = 1; dy <= 2; dy++) {
					for (int dx = 2; dx >= 1; dx--) {
						if (dy != dx) {
							break;
						}

						if (niz[(x - dx) * 8 + y + dy].equals("0") || niz[(x - dx) * 8 + y + dy].charAt(0) == enemy) {
							result[(x - dx) * 8 + y + dy] = 1;
						}

						if (niz[(x + dx) * 8 + y + dy].equals("0") || niz[(x + dx) * 8 + y + dy].charAt(0) == enemy) {
							result[(x + dx) * 8 + y + dy] = 1;
						}

					}
				}

			} else if (x == 7) {

				for (int dy = -2; dy <= 2; dy++) {
					if (dy == 0) {
						break;
					}

					for (int dx = -1; dx >= -2; dx--) {
						if (dy == dx) {
							break;
						}

						if (niz[(x + dx) * 8 + y + dy].equals("0") || niz[(x + dx) * 8 + y + dy].charAt(0) == enemy) {
							result[(x + dx) * 8 + y + dy] = 1;
						}

						if (niz[(x + dx) * 8 + y + dy].equals("0") || niz[(x + dx) * 8 + y + dy].charAt(0) == enemy) {
							result[(x + dx) * 8 + y + dy] = 1;
						}

					}
				}

			} else if (y == 7) {

				for (int dx = -2; dx <= 2; dx++) {
					if (dx == 0) {
						break;
					}

					for (int dy = -1; dy >= -2; dy--) {
						if (dy == dx) {
							break;
						}

						if (niz[(x + dx) * 8 + y + dy].equals("0") || niz[(x + dx) * 8 + y + dy].charAt(0) == enemy) {
							result[(x + dx) * 8 + y + dy] = 1;
						}

						if (niz[(x + dx) * 8 + y + dy].equals("0") || niz[(x + dx) * 8 + y + dy].charAt(0) == enemy) {
							result[(x + dx) * 8 + y + dy] = 1;
						}

					}
				}

			} else if (x == 1) {

				for (int dy = -2; dy <= 2; dy++) {
					if (dy == 0) {
						break;
					}

					for (int dx = -1; dx <= 2; dx++) {
						if (dx == 0) {
							break;
						}

						if (dy == Math.abs(dx)) {
							break;
						}

						if (niz[(x + dx) * 8 + y + dy].equals("0") || niz[(x + dx) * 8 + y + dy].charAt(0) == enemy) {
							result[(x + dx) * 8 + y + dy] = 1;
						}

						if (niz[(x + dx) * 8 + y + dy].equals("0") || niz[(x + dx) * 8 + y + dy].charAt(0) == enemy) {
							result[(x + dx) * 8 + y + dy] = 1;
						}

					}
				}

			} else if (y == 1) {

				for (int dx = -2; dx <= 2; dx++) {
					if (dx == 0) {
						break;
					}

					for (int dy = -1; dy <= 2; dy++) {
						if (dy == 0) {
							break;
						}

						if (dy == Math.abs(dx)) {
							break;
						}

						if (niz[(x + dx) * 8 + y + dy].equals("0") || niz[(x + dx) * 8 + y + dy].charAt(0) == enemy) {
							result[(x + dx) * 8 + y + dy] = 1;
						}

						if (niz[(x + dx) * 8 + y + dy].equals("0") || niz[(x + dx) * 8 + y + dy].charAt(0) == enemy) {
							result[(x + dx) * 8 + y + dy] = 1;
						}

					}
				}

			} else if (x == 6) {

				for (int dy = -2; dy <= 2; dy++) {
					if (dy == 0) {
						break;
					}

					for (int dx = -2; dx <= 1; dx++) {
						if (dx == 0) {
							break;
						}

						if (dy == Math.abs(dx)) {
							break;
						}

						if (niz[(x + dx) * 8 + y + dy].equals("0") || niz[(x + dx) * 8 + y + dy].charAt(0) == enemy) {
							result[(x + dx) * 8 + y + dy] = 1;
						}

						if (niz[(x + dx) * 8 + y + dy].equals("0") || niz[(x + dx) * 8 + y + dy].charAt(0) == enemy) {
							result[(x + dx) * 8 + y + dy] = 1;
						}

					}
				}

			} else if (y == 6) {

				for (int dx = -2; dx <= 2; dx++) {
					if (dx == 0) {
						break;
					}

					for (int dy = -2; dx <= 1; dx++) {
						if (dy == 0) {
							break;
						}

						if (dy == Math.abs(dx)) {
							break;
						}

						if (niz[(x + dx) * 8 + y + dy].equals("0") || niz[(x + dx) * 8 + y + dy].charAt(0) == enemy) {
							result[(x + dx) * 8 + y + dy] = 1;
						}

						if (niz[(x + dx) * 8 + y + dy].equals("0") || niz[(x + dx) * 8 + y + dy].charAt(0) == enemy) {
							result[(x + dx) * 8 + y + dy] = 1;
						}

					}
				}

			} else {

				// konj se ne nalazi u blizini bilo kakvih rubova
				for (int dy = -1; dy <= 1; dy++) {
					if (dy != 0) {
						if (niz[(x - 2) * 8 + y + dy].equals("0") || niz[(x - 2) * 8 + y + dy].charAt(0) == enemy) {
							result[(x - 2) * 8 + y + dy] = 1;
						}

						if (niz[(x + 2) * 8 + y + dy].equals("0") || niz[(x + 2) * 8 + y + dy].charAt(0) == enemy) {
							result[(x + 2) * 8 + y + dy] = 1;
						}
					}
				}

				for (int dx = -1; dx <= 1; dx++) {
					if (dx != 0) {
						if (niz[(x + dx) * 8 + y - 2].equals("0") || niz[(x + dx) * 8 + y - 2].charAt(0) == enemy) {
							result[(x + dx) * 8 + y - 2] = 1;
						}

						if (niz[(x + dx) * 8 + y - 2].equals("0") || niz[(x + dx) * 8 + y - 2].charAt(0) == enemy) {
							result[(x + dx) * 8 + y - 2] = 1;
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
