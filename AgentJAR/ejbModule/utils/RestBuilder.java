package utils;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import model.agent.AID;
import model.agent.AgentType;
import model.center.AgentCenter;
import node_manager.NodeManagerLocal;

public class RestBuilder {

	public static void contactMaster() {
		try {
			Context context = new InitialContext();
			NodeManagerLocal nml = (NodeManagerLocal) context.lookup(NodeManagerLocal.LOOKUP); 
			AgentCenter master = nml.getMasterNode();
			
			ResteasyClient client = new ResteasyClientBuilder().build();
	        ResteasyWebTarget target = client.target(master.getAddress() + "/AgentWAR");
	        RestAPI rest = target.proxy(RestAPI.class);
	        
	        rest.connectNodes(nml.getThisNode());
	    } catch (Exception e) {
	    	 System.out.println("Cause: " + e.getCause());
	         System.out.println("Message: " + e.getMessage());
	         System.out.println("Class: " + e.getClass());
	         System.out.println("StackTrace: " + e.getStackTrace());
			//e.printStackTrace();
		}
	}
	
	public static List<AgentType> getSlaveAgentTypes(AgentCenter slaveAddr) {
		try {
			Context context = new InitialContext();
			NodeManagerLocal nml = (NodeManagerLocal) context.lookup(NodeManagerLocal.LOOKUP); 
			AgentCenter master = nml.getMasterNode();
			
			ResteasyClient client = new ResteasyClientBuilder().build();
	        ResteasyWebTarget target = client.target(master.getAddress() + "/AgentWAR");
	        RestAPI rest = target.proxy(RestAPI.class);
	        
	        List<AgentType> types = rest.getAgentTypes();
	        return types;
	    } catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}	
	
	public static void sendNewSlave(AgentCenter oldSlave, AgentCenter newSlave) {
		// saljemo nekom slaveu da se novi slave ukljucio u mrezu
		try {
			Context context = new InitialContext();
			NodeManagerLocal nml = (NodeManagerLocal) context.lookup(NodeManagerLocal.LOOKUP); 
			AgentCenter master = nml.getMasterNode();
			
			ResteasyClient client = new ResteasyClientBuilder().build();
	        ResteasyWebTarget target = client.target(master.getAddress() + "/AgentWAR");
	        RestAPI rest = target.proxy(RestAPI.class);
	        
	        rest.connectNodes(nml.getThisNode());
	    } catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sendNewAgentTypes(AgentCenter oldSlave, List<AgentType> newSlaveAgentTypes) {
		try {
			ResteasyClient client = new ResteasyClientBuilder().build();
	        ResteasyWebTarget target = client.target(oldSlave.getAddress() + "/AgentWAR");
	        RestAPI rest = target.proxy(RestAPI.class);
	        
	        rest.newAgentTypes(newSlaveAgentTypes);
	    } catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sendNodesToSlave(AgentCenter newSlave, List<AgentCenter> nodes) {
		try {
			ResteasyClient client = new ResteasyClientBuilder().build();
	        ResteasyWebTarget target = client.target(newSlave.getAddress() + "/AgentWAR");
	        RestAPI rest = target.proxy(RestAPI.class);
	        
	        for(AgentCenter a : nodes)
	        	rest.connectNodes(a);
	    } catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sendRunningAgentsToSlave(AgentCenter newSlave, List<AID> runningAgents) {
		// master cvor slaje spisak pokrenutih agenata novom cvoru
		try {
			ResteasyClient client = new ResteasyClientBuilder().build();
	        ResteasyWebTarget target = client.target(newSlave.getAddress() + "/AgentWAR");
	        RestAPI rest = target.proxy(RestAPI.class);
	        
	        rest.addRunningAgents(runningAgents);
	    } catch (Exception e) {
			e.printStackTrace();
		}
	}
}
