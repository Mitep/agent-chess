package agent_manager;

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

@Singleton
@Startup
public class AgentManager implements AgentManagerLocal {

	private HashMap<AID, AgentClass> runningAgents;

	public AgentManager() {
	}

	@PostConstruct
	private void initAgentManager() {
		runningAgents = new HashMap<AID, AgentClass>();
	}

	@Override
	public List<AID> getRunningAgents() {
		return new ArrayList<>(runningAgents.keySet());
	}

	@Override
	public List<AgentType> getAgentTypes() {
		ArrayList<AgentType> tipovi = new ArrayList<>();
		for (AID id : runningAgents.keySet()) {
			tipovi.add(id.getType());
		}
		return tipovi;
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
		if (runningAgents.containsKey(agent)) {
			System.out.println("Vec postoji agent s tim identifikatorom!");
		}

		try {
			Object obj = Class.forName(agent.getType().toString()).newInstance();
			if (obj instanceof AgentClass) {
				runningAgents.put(agent, (AgentClass) obj);
			} else {
				System.out.println("Agent tipa " + agent.getType() + " se dodati u mapu!");
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
		if (runningAgents.containsKey(agent)) {
			runningAgents.remove(agent);
		} else {
			System.out.println("Ne postoji agent s tim identifikatorom!");
		}
	}

}
