package services.interfaces;

import javax.ejb.Local;

import model.center.AgentCenter;

@Local
public interface RestNodeLocal {

	public static String LOOKUP = "java:app/RestNode!services.interfaces.RestNodeLocal";
	
	// master trazi listu tipova agenata od slavea
	void connectSlave(AgentCenter slaveAddr);
	
	// post node 
	void contactMaster(AgentCenter slaveAddr);
	
}
