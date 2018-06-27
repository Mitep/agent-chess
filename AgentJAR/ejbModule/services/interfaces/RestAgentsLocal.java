package services.interfaces;

import java.util.List;

import javax.ejb.Local;

import model.agent.AID;
import model.agent.AgentType;

@Local
public interface RestAgentsLocal {

	List<AgentType> getAgentsClasses();
	List<AID> getAgentsRunning();
	void putAgentsRunning(String type, String name);
	void deleteAgentsRunning(String aid);
	
}
