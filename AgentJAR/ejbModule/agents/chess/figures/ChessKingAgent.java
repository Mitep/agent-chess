package agents.chess.figures;

import java.util.HashMap;

import model.acl.ACLMessage;
import model.acl.Performative;
import model.agent.AgentClass;

public class ChessKingAgent extends AgentClass {

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

			if (x == 0 && y == 0) {

				// kralj se nalazi u gornjem levom cosku
				if (niz[1].equals("0") || niz[1].charAt(0) == 'p') {
					result[1] = 1;
				}

				if (niz[8].equals("0") || niz[8].charAt(0) == 'p') {
					result[8] = 1;
				}

				if (niz[9].equals("0") || niz[9].charAt(0) == 'p') {
					result[9] = 1;
				}

			} else if (x == 7 && y == 7) {

				// kralj se nalazi u donjem desnom cosku
				if (niz[54].equals("0") || niz[54].charAt(0) == 'p') {
					result[54] = 1;
				}

				if (niz[55].equals("0") || niz[55].charAt(0) == 'p') {
					result[55] = 1;
				}

				if (niz[62].equals("0") || niz[62].charAt(0) == 'p') {
					result[62] = 1;
				}

			} else if (x == 7 && y == 0) {

				// kralj se nalazi u donjem levom cosku
				if (niz[48].equals("0") || niz[48].charAt(0) == 'p') {
					result[48] = 1;
				}

				if (niz[49].equals("0") || niz[49].charAt(0) == 'p') {
					result[49] = 1;
				}

				if (niz[57].equals("0") || niz[57].charAt(0) == 'p') {
					result[57] = 1;
				}

			} else if (x == 0 && y == 7) {

				// kralj se nalazi u gornjem desnom cosku
				if (niz[6].equals("0") || niz[6].charAt(0) == 'p') {
					result[6] = 1;
				}

				if (niz[14].equals("0") || niz[14].charAt(0) == 'p') {
					result[14] = 1;
				}

				if (niz[15].equals("0") || niz[15].charAt(0) == 'p') {
					result[15] = 1;
				}

			} else if (x == 0) {

				// kralj se nalazi na levom rubu table
				for (int dx = 0; dx <= 1; dx++) {
					for (int dy = -1; dy <= 1; dy++) {
						if (dx != dy) {
							if (niz[(x + dx) * 8 + y + dy].equals("0")) {
								result[(x + dx) * 8 + y + dy] = 1;
							} else if (niz[(x + dx) * 8 + y + dy].charAt(0) == 'p') {
								result[(x + dx) * 8 + y + dy] = 1;
								break;
							}
						}
					}
				}

			} else if (y == 0) {

				// kralj se nalazi na gornjem rubu table
				for (int dx = -1; dx <= 1; dx++) {
					for (int dy = 0; dy <= 1; dy++) {
						if (dx != dy) {
							if (niz[(x + dx) * 8 + y + dy].equals("0")) {
								result[(x + dx) * 8 + y + dy] = 1;
							} else if (niz[(x + dx) * 8 + y + dy].charAt(0) == 'p') {
								result[(x + dx) * 8 + y + dy] = 1;
								break;
							}
						}
					}
				}

			} else if (x == 7) {

				// kralj se nalazi na donjem rubu table
				for (int dx = -1; dx <= 0; dx++) {
					for (int dy = -1; dy <= 1; dy++) {
						if (dx != dy) {
							if (niz[(x + dx) * 8 + y + dy].equals("0")) {
								result[(x + dx) * 8 + y + dy] = 1;
							} else if (niz[(x + dx) * 8 + y + dy].charAt(0) == 'p') {
								result[(x + dx) * 8 + y + dy] = 1;
								break;
							}
						}
					}
				}
				
			} else if (y == 7) {

				// kralj se nalazi na desnom rubu table
				for (int dx = -1; dx <= 1; dx++) {
					for (int dy = -1; dy <= 0; dy++) {
						if (dx != dy) {
							if (niz[(x + dx) * 8 + y + dy].equals("0")) {
								result[(x + dx) * 8 + y + dy] = 1;
							} else if (niz[(x + dx) * 8 + y + dy].charAt(0) == 'p') {
								result[(x + dx) * 8 + y + dy] = 1;
								break;
							}
						}
					}
				}
				
			} else {
				// kralj se ne nalazi blizu ruba
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						if (i != j) {
							if (niz[(x + i) * 8 + y + j].equals("0")) {
								result[(x + i) * 8 + y + j] = 1;
							} else if (niz[(x + i) * 8 + y + j].charAt(0) == 'p') {
								result[(x + i) * 8 + y + j] = 1;
								break;
							}
						}
					}
				}
			}
		} else {
			System.out.println("Error: Unexpected message.");
		}

	}

}