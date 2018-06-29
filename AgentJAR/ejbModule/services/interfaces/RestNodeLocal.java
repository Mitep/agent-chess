package services.interfaces;

import java.util.List;

import javax.ejb.Local;

import model.center.AgentCenter;

@Local
public interface RestNodeLocal {

	public static String LOOKUP = "java:app/RestNode!services.interfaces.RestNodeLocal";
	
	// master trazi listu tipova agenata od slavea
	void connectSlave(AgentCenter slaveAddr);
	
	// master salje novi cvor ostalim cvorovima
	void newSlaveNode(AgentCenter newSlave);
	
	// master salje novom cvoru ostale cvorove
	void newSlavesNode(List<AgentCenter> slaves);
	
	void deleteNode(String alias);
	
}
