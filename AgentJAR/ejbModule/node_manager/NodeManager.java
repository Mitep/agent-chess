package node_manager;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import model.center.AgentCenter;

@Singleton
@Startup
public class NodeManager implements NodeManagerLocal {

	private List<AgentCenter> nodes;
	private AgentCenter masterNode;
	private AgentCenter thisNode;
	
	public NodeManager() {
		nodes = new ArrayList<AgentCenter>();
	}
	
	@PostConstruct
	public void nodeInit() {
		
	}

	@Override
	public void setMasterNode(AgentCenter ac) {
		this.masterNode = ac;
	}

	@Override
	public void setThisNode(AgentCenter ac) {
		this.thisNode = ac;
	}

	@Override
	public AgentCenter getMasterNode() {
		return masterNode;
	}

	@Override
	public AgentCenter getThisNode() {
		return thisNode;
	}
	
}
