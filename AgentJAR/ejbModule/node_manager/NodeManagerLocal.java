package node_manager;

import java.util.List;

import javax.ejb.Local;

import model.agent.AgentType;
import model.center.AgentCenter;

@Local
public interface NodeManagerLocal {

	public static String LOOKUP = "java:app/AgentJAR/NodeManager!node_manager.NodeManagerLocal";

	AgentCenter getMasterNode();
	AgentCenter getThisNode();
	List<AgentCenter> getSlaves();
	void deleteSlave(AgentCenter slave);
	void addSlave(AgentCenter slave, List<AgentType> slaveAgentTypes);
	
}
