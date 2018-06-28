package node_manager;

import javax.ejb.Local;

import model.center.AgentCenter;

@Local
public interface NodeManagerLocal {

	public static String LOOKUP = "java:app/AgentJAR/NodeManager!node_manager.NodeManagerLocal";

	AgentCenter getMasterNode();
	AgentCenter getThisNode();
	
}
