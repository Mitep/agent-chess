package agent_manager;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import model.acl.ACLMessage;
import model.agent.AID;
import model.agent.AgentClass;

@Singleton
@Startup
public class AgentManager implements AgentManagerLocal {

	private HashMap<AID, AgentClass> runningAgents;
	
	public AgentManager() {}
	
	@PostConstruct
	private void initAgentManager() {
		runningAgents = new HashMap<AID, AgentClass>();
	}
	
	@Override
	public List<String> getRunningAgents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAgentTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void msgToAgent(AID agent, ACLMessage msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startAgent(AID agent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopAgent(AID agent) {
		// TODO Auto-generated method stub
		
	}

}
