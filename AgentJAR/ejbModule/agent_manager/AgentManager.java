package agent_manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import model.acl.ACLMessage;
import model.agent.AID;
import model.agent.AgentClass;
import model.agent.AgentType;
import model.center.AgentCenter;

@Singleton
@Startup
public class AgentManager implements AgentManagerLocal {

	private HashMap<AID, AgentClass> runningAgents;
	private List<AgentType> agentTypes;
	private AgentCenter host;

	public AgentManager() {
	}

	@PostConstruct
	private void initAgentManager() {
		setHost();
		
		runningAgents = new HashMap<AID, AgentClass>();
		initAgentTypes();
	}
	
	private void setHost() {
		final File configFile = new File(
				AgentManagerLocal.class.getProtectionDomain().getCodeSource().getLocation().getPath() + File.separator
				+ "META-INF" + File.separator + "node_config.txt");
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(configFile));
			String masterHost = br.readLine();	
			String myHost = br.readLine();
			
			this.host = new AgentCenter(myHost);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initAgentTypes() {
		agentTypes = new ArrayList<AgentType>();

		final File basePackage = new File(
				AgentManagerLocal.class.getProtectionDomain().getCodeSource().getLocation().getPath() + File.separator
						+ "agents");
		System.out.println(AgentManagerLocal.class.getProtectionDomain().getCodeSource().getLocation().getPath());

		agentTypes = processFile(basePackage);

		for (AgentType at : agentTypes) {
			System.out.println(at);
		}
	}

	private ArrayList<AgentType> processFile(File f) {
		ArrayList<AgentType> types = new ArrayList<>();
		if (f.isDirectory()) {
			for (File file : f.listFiles()) {
				ArrayList<AgentType> tmp = processFile(file);
				types.addAll(tmp);
			}
		}

		if (f.isFile()) {
			File parent = f.getParentFile();
			String module = parent.getPath().substring(parent.getPath().lastIndexOf("agents"));
			System.out.println(module);
			module = module.replace(File.separatorChar, '.');
			String name = f.getName();
			name = name.substring(0, name.indexOf("."));
			AgentType at = new AgentType(name, module);
			types.add(at);
		}

		return types;
	}

	@Override
	public List<AID> getRunningAgents() {
		return new ArrayList<>(runningAgents.keySet());
	}

	@Override
	public List<AgentType> getAgentTypes() {
		return agentTypes;
	}

	@Override
	public boolean msgToAgent(AID agent, ACLMessage msg) {
		AgentClass receiver = runningAgents.get(agent);
		if (receiver != null) {
			receiver.handleMessage(msg);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void startAgent(AID agent) {
		if (containsAgent(agent)) {
			System.out.println("Vec postoji agent s tim identifikatorom!");
		}

		try {
			Object obj = Class.forName(agent.getType().toString()).newInstance();
			if (obj instanceof AgentClass) {
				((AgentClass) obj).setId(agent);
				runningAgents.put(agent, (AgentClass) obj);
			} else {
				System.out.println("Agent tipa " + agent.getType() + " se ne moze dodati u mapu!");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void stopAgent(AID agent) {
		if (containsAgent(agent)) {
			runningAgents.remove(agent);
		} else {
			System.out.println("Ne postoji agent s tim identifikatorom!");
		}
	}

	@Override
	public AgentType getAgentType(String name, String module) {
		for (AgentType at : agentTypes) {
			if (at.getName().equals(name) && at.getModule().equals(module)) {
				return at;
			}
		}
		return null;
	}

	@Override
	public AgentCenter getAgentCenter() {
		return host;
	}

	private boolean containsAgent(AID key) {
		for (AID tmp : runningAgents.keySet()) {
			if (tmp.getHost().equals(key.getHost()) && tmp.getName().equals(key.getName())
					&& tmp.getType().equals(key.getType()))
				return true;
		}
		return false;
	}

	public void setAgentCenter(AgentCenter c) {
		this.host = c;
	}
}
