package node_manager;

import javax.ejb.Local;

import model.center.AgentCenter;

@Local
public interface NodeManagerLocal {

	public static String LOOKUP = "java:app/AgentJAR/NodeManager!node_manager.NodeManagerLocal";

	void setMasterNode(AgentCenter ac);
	void setThisNode(AgentCenter ac);
	AgentCenter getMasterNode();
	AgentCenter getThisNode();
	
}
