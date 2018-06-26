package agent_manager;

import java.util.List;

import javax.ejb.Local;

import model.acl.ACLMessage;
import model.agent.AID;

@Local
public interface AgentManagerLocal {

	List<String> getRunningAgents();
	List<String> getAgentTypes();
	void msgToAgent(AID agent, ACLMessage msg);
	void startAgent(AID agent);
	void stopAgent(AID agent);
	
}
