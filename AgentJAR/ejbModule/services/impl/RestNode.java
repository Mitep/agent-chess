package services.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;

import agent_manager.AgentManagerLocal;
import model.agent.AgentType;
import model.center.AgentCenter;
import node_manager.NodeManagerLocal;
import services.interfaces.RestNodeLocal;
import utils.RestBuilder;

@Stateless
public class RestNode implements RestNodeLocal {

	@Override
	public void connectSlave(AgentCenter slaveAddr) {
		// trazimo od slavea sve tipove agenata
		List<AgentType> slaveTypes = RestBuilder.getSlaveAgentTypes(slaveAddr);
		
		// javljamo ostalim cvorovima da je slave dosao
		try {
			Context context = new InitialContext();
			NodeManagerLocal nml = (NodeManagerLocal) context.lookup(NodeManagerLocal.LOOKUP);
			List<AgentCenter> slaves = nml.getSlaves();

			for(AgentCenter ac : slaves) {
				// master cvor javlja ostalim cvorovima da je novi cvor dosao u mrezu				
				RestBuilder.sendNewSlave(ac, slaveAddr);
				// master cvor dostalja ostalim cvorovima spisak tipova agenata novog cvora				
				RestBuilder.sendNewAgentTypes(ac, slaveTypes);
			}
			// master cvor dostavlja spisak agenata novom cvoru
			RestBuilder.sendNodesToSlave(slaveAddr, slaves);
			
			// master cvor dostavlja spisak tipova agenata novom cvoru
			AgentManagerLocal aml = (AgentManagerLocal) context.lookup(AgentManagerLocal.LOOKUP);
			RestBuilder.sendAgentTypesToSlave(slaveAddr, aml.getAgentTypes());
			
			// master cvor dostavlja spisak tipova agenata novom cvoru
			RestBuilder.sendRunningAgentsToSlave(slaveAddr, aml.getRunningAgents());
			
			// na ovaj cvor dodamo novi cvor i njegove tipove
			nml.addSlave(slaveAddr, slaveTypes);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 		
	}

	@Override
	public void newSlaveNode(AgentCenter newSlave) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newSlavesNode(List<AgentCenter> slaves) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteNode(String alias) {
		// TODO Auto-generated method stub
		
	}

}
