package node_manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import agent_manager.AgentManagerLocal;
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
		final File configFile = new File(
				AgentManagerLocal.class.getProtectionDomain().getCodeSource().getLocation().getPath() + File.separator
				+ "META-INF" + File.separator + "node_config.txt");
		try {
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(configFile));
			
			String masterHost = br.readLine();
			String thisHost = br.readLine();
			this.masterNode = new AgentCenter(masterHost);
			this.thisNode = new AgentCenter(thisHost);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
