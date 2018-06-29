package agent_manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.Context;
import javax.naming.InitialContext;

import model.acl.ACLMessage;
import model.agent.AID;
import model.agent.AgentClass;
import model.agent.AgentType;
import model.center.AgentCenter;
import services.interfaces.WebSocketLocal;
import utils.JsonUtils;

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
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(configFile));
			
			@SuppressWarnings("unused")
			String masterHost = br.readLine();
			String thisHost = br.readLine();
			this.host = new AgentCenter(thisHost);
		} catch (Exception e) {
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
			
			try {
				Context context = new InitialContext();
				WebSocketLocal wsl = (WebSocketLocal) context.lookup(WebSocketLocal.LOOKUP);
				wsl.sendMessage(JsonUtils.getACLMessageString(msg));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void startAgent(AID agent) {
		AID a = containsAgent(agent);
		if (a != null) {
			System.out.println("Vec postoji agent s tim identifikatorom!");
			return;
		}

		try {
			Object obj = Class.forName(agent.getType().toString()).newInstance();
			if (obj instanceof AgentClass) {
				((AgentClass) obj).setId(agent);
				runningAgents.put(agent, (AgentClass) obj);
				
				Context context = new InitialContext();
				WebSocketLocal wsl = (WebSocketLocal) context.lookup(WebSocketLocal.LOOKUP);
				wsl.sendMessage(JsonUtils.getAIDString(agent, true));
			} else {
				System.out.println("Agent tipa " + agent.getType() + " se ne moze dodati u mapu!");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e ) {
			e.printStackTrace();
		}

	}

	@Override
	public void stopAgent(AID agent) {
		AID a = containsAgent(agent);
		if (a != null) {
			runningAgents.remove(a);
			Context context;
			try {
				context = new InitialContext();
				WebSocketLocal wsl = (WebSocketLocal) context.lookup(WebSocketLocal.LOOKUP);
				wsl.sendMessage(JsonUtils.getAIDString(agent, false));
			} catch (Exception e) {
				e.printStackTrace();
			}
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

	private AID containsAgent(AID key) {
		for (AID tmp : runningAgents.keySet()) {
			if (tmp.getHost().getAlias().equals(key.getHost().getAlias()) && tmp.getHost().getAddress().equals(key.getHost().getAddress()) && tmp.getName().equals(key.getName())
					&& tmp.getType().getName().equals(key.getType().getName()) && tmp.getType().getModule().equals(key.getType().getModule()))
				return tmp;
		}
		return null;
	}

	public void setAgentCenter(AgentCenter c) {
		this.host = c;
	}
}
