package utils;

import java.util.List;

import model.agent.AID;
import model.agent.AgentType;
import model.center.AgentCenter;

public class RestBuilder {

	public static void contactMaster() {
		// rest bilder 
		//post /node
	}
	
	public static List<AgentType> getSlaveAgentTypes(AgentCenter slaveAddr) {
		// trazimo od slavea spisak tipova agenata
		return null;
	}	
	
	public static void sendNewSlave(AgentCenter oldSlave, AgentCenter newSlave) {
		// saljemo nekom slaveu da se novi slave ukljucio u mrezu
	}
	
	public static void sendNewAgentTypes(AgentCenter oldSlave, List<AgentType> newSlaveAgentTypes) {
		// saljemo nekom slaveu nove tipove agenata 
	}
	
	public static void sendNodesToSlave(AgentCenter newSlave, List<AgentCenter> nodes) {
		// master cvor salje novom cvoru spisak trenutnih cvorova
	}
	
	public static void sendAgentTypesToSlave(AgentCenter newSlave, List<AgentType> Agenttypes) {
		// master cvor salje spisak tipova agenata novom cvoru
	}
	
	public static void sendRunningAgentsToSlave(AgentCenter newSlave, List<AID> runningAgents) {
		// master cvor slaje spisak pokrenutih agenata novom cvoru
	}
}
