package services.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import agent_manager.AgentManagerLocal;
import model.agent.AID;
import model.agent.AgentType;
import services.interfaces.RestAgentsLocal;

@Stateless
public class RestAgents implements RestAgentsLocal {

	@Override
	public List<AgentType> getAgentsClasses() {
		try {
			Context context = new InitialContext();
			AgentManagerLocal aml = (AgentManagerLocal) context.lookup(AgentManagerLocal.LOOKUP);
			return aml.getAgentTypes();
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<AID> getAgentsRunning() {
		try {
			Context context = new InitialContext();
			AgentManagerLocal aml = (AgentManagerLocal) context.lookup("");
			return aml.getRunningAgents();
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void putAgentsRunning(String type, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAgentsRunning(String aid) {
		// TODO Auto-generated method stub
		
	}

}
